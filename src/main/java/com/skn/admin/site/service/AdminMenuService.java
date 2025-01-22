package com.skn.admin.site.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skn.admin.config.api.exception.GeneralException;
import com.skn.admin.site.dto.AdminGroup;
import com.skn.admin.site.dto.AdminGroupPermission;
import com.skn.admin.site.dto.AdminMenu;
import com.skn.admin.site.dto.menu.request.MenuRequest;
import com.skn.admin.site.mapper.AdminGroupOldMapper;
import com.skn.admin.site.mapper.AdminGroupPermissionMapper;
import com.skn.admin.site.mapper.AdminMenuMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.*;
import java.util.stream.Collectors;

import static com.skn.admin.config.api.constant.ErrorCode.BAD_REQUEST;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminMenuService {
	private final AdminMenuMapper adminMenuMapper;
	private final AdminGroupOldMapper adminGroupOldMapper;
	private final AdminGroupPermissionMapper adminGroupPerMissionMapper;

	@Transactional(readOnly = true)
	public void selectMenuInInfo(
		String type,
		String uid,
		Model model
	) {
		try {
			if (type.contains("put")) {
				model.addAttribute("list", this.selectMenu(uid));
			}

		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Transactional(readOnly = true)
	public void selectMenuList(Model model) throws JsonProcessingException {
		List<AdminMenu> menuList = selectMenuListAll();

		ObjectMapper objectMapper = new ObjectMapper();
		String depth1List = objectMapper.writeValueAsString(menuList
			.stream()
			.filter(menu -> menu.getDepth() == 1)
			.collect(Collectors.toList()));
		String depth2List = objectMapper.writeValueAsString(menuList
			.stream()
			.filter(menu -> menu.getDepth() == 2)
			.collect(Collectors.toList()));
		String depth3List = objectMapper.writeValueAsString(menuList
			.stream()
			.filter(menu -> menu.getDepth() == 3)
			.collect(Collectors.toList()));

		model.addAttribute("depth1Json", depth1List);
		model.addAttribute("depth2Json", depth2List);
		model.addAttribute("depth3Json", depth3List);

		model.addAttribute("depth1List", menuList
			.stream()
			.filter(menu -> menu.getDepth() == 1)
			.collect(Collectors.toList()));
		model.addAttribute("depth2List", menuList
			.stream()
			.filter(menu -> menu.getDepth() == 2)
			.collect(Collectors.toList()));
		model.addAttribute("depth3List", menuList
			.stream()
			.filter(menu -> menu.getDepth() == 3)
			.collect(Collectors.toList()));
	}

	@Transactional(readOnly = true)
	public AdminMenu selectMenu(String uid) {
		Optional<AdminMenu> menu = adminMenuMapper.selectMenu(uid);

		if(menu.isEmpty()) throw new GeneralException(BAD_REQUEST);

		return menu.get();
	}

	@Transactional(readOnly = true)
	public List<AdminMenu> selectMenuListAll() {
		List<AdminMenu> menuList = Collections.singletonList(AdminMenu.toEmpty());
		try {
			menuList = adminMenuMapper.selectMenuListAll();

			return menuList;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return menuList;
	}

	@Transactional(rollbackFor = Exception.class)
	public AdminMenu insertMenu(
		MenuRequest.Insert insert
	) {
		AdminMenu menu = new AdminMenu(insert);
		adminMenuMapper.insertMenu(menu);

		/**
		 *  Admin Group Permission 등록
		 */
		List<AdminGroup> adminGroupList = adminGroupOldMapper.selectAdminGroupList(null);
		adminGroupList.forEach(adminGroup -> {
			AdminGroupPermission adminGroupPermission = new AdminGroupPermission(
				adminGroup.getAdminGroupIndex(),
				menu.getMenuIndex(),
				menu.getAdminId(),
				menu.getAdminIndex(),
				"N"
			);
			adminGroupPerMissionMapper.insertAdminGroupPermission2(adminGroupPermission);
		});
		return menu;
	}

	@Transactional(rollbackFor = Exception.class)
	public AdminMenu updateMenu(
		String uid,
		MenuRequest.Update update
	) {
		AdminMenu menu = new AdminMenu(update, uid);

		adminMenuMapper.updateMenu(menu);

		return menu;
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteMenu(String uid) {
		adminMenuMapper.deleteMenu(uid);
	}

	public List<AdminMenu> selectTopMenuList(MenuRequest.TopMenu topMenu) {
		List outList = null;

		try {
			outList = adminMenuMapper.selectTopMenuList(topMenu);
		} catch (DataAccessException ex) {
			throw ex;
		} catch (Exception ex) {
			throw ex;
		}

		return outList;
	}


	@Transactional(readOnly = true)
	public List<AdminMenu> selectLeftMenuList(MenuRequest.LeftMenu leftMenu) {
		List<AdminMenu> menuList = adminMenuMapper.selectLeftMenuList(leftMenu);
		// 없을 경우 uid를 제거 후 돌린다.
		if (menuList == null || menuList.size() < 1) {
			String remainUrl = removeLastSegment(leftMenu.getUrl());
			leftMenu.setUrl(remainUrl);
			menuList = adminMenuMapper.selectLeftMenuList(leftMenu);
		}
		return menuList;
	}

	public AdminMenu selectMenu(MenuRequest.Menu insert) {
		AdminMenu menu = null;
		try {
			menu = adminMenuMapper.selectAdminMenu(insert).stream().map(x-> {
				String updatedLocation = x.getLocation()
					.replaceAll("::", ">")
					.replaceAll("<a ", "<a style=\"color: #13795b;\" ");
				x.setLocation(updatedLocation);

				return x;
			}).findFirst().orElse(null);
			// 없을 경우 uid를 제거 후 돌린다.
			if (menu == null) {
				String remainUrl = removeLastSegment(insert.getUrl());
				insert.setUrl(remainUrl);
				menu = adminMenuMapper.selectAdminMenu(insert).stream().map(x-> {
					String updatedLocation = x.getLocation()
						.replaceAll("::", ">")
						.replaceAll("<a ", "<a style=\"color: #13795b;\" ");
					x.setLocation(updatedLocation);

					return x;
				}).findFirst().orElse(null);
			}
		} catch (Exception e) {
			throw e;
		}

		return menu;
	}
	public boolean init(AdminMenu adminMenu) {
		return true;
	}

	public boolean isExistByAdminGroupIndexAndUrl(Integer adminGroupIndex, String url) {
		return adminMenuMapper.isExistByAdminGroupIndexAndUrl(adminGroupIndex, url);
	}

	public static String removeLastSegment(String url) {
		// URL에서 마지막 '/'의 인덱스를 찾습니다.
		int lastIndex = url.lastIndexOf('/');

		// '/'가 없으면 원래 URL을 반환합니다.
		if (lastIndex <= 0) {
			return url;
		}

		// 마지막 '/' 이전까지의 부분을 반환합니다.
		return url.substring(0, lastIndex);
	}
}
