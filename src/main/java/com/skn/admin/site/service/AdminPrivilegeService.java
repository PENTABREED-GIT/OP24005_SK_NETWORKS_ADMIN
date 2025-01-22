package com.skn.admin.site.service;

import com.skn.admin.base.etc.Page;
import com.skn.admin.config.api.exception.GeneralException;
import com.skn.admin.site.dto.AdminGroup;
import com.skn.admin.site.dto.AdminGroupPermission;
import com.skn.admin.site.dto.adminprivilege.AdminPrivilege;
import com.skn.admin.site.dto.adminprivilege.AdminPrivilegeMenu;
import com.skn.admin.site.dto.adminprivilege.request.AdminPrivilegeReqeust;
import com.skn.admin.site.dto.adminprivilege.request.AdminPrivilegeSearchParam;
import com.skn.admin.site.dto.AdminMenu;
import com.skn.admin.site.mapper.AdminGroupOldMapper;
import com.skn.admin.site.mapper.AdminGroupPermissionMapper;
import com.skn.admin.site.mapper.AdminMenuMapper;
import com.skn.admin.site.dto.adminprivilege.response.ResponseMenuDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.skn.admin.config.api.constant.ErrorCode.BAD_REQUEST;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminPrivilegeService {
	private final AdminMenuMapper adminMenuMapper;
	private final AdminGroupOldMapper adminGroupOldMapper;
	private final AdminGroupPermissionMapper adminGroupPerMissionMapper;

	public void getAdminPrivilegeList(AdminPrivilegeSearchParam _param, Page paging, Model model) {
		int totalCount = 0;
		AdminPrivilegeSearchParam params = AdminPrivilegeSearchParam.of(_param, paging);
		List<AdminGroup> list = this.selectAdminPrivilegeList(params);
		totalCount = this.selectAdminPrivilegeTotalCount(params);
		paging.setPage(totalCount);

		model.addAttribute("list", list)
			.addAttribute("page", paging)
			.addAttribute("listNum", totalCount)
			.addAttribute("search", params);
	}

	@Transactional(readOnly = true)
	public List<AdminGroup> selectAdminPrivilegeList(AdminPrivilegeSearchParam param) {
		return adminGroupOldMapper.selectAdminPrivilegeList(param);
	}
	@Transactional(readOnly = true)
	public int selectAdminPrivilegeTotalCount(AdminPrivilegeSearchParam param) {
		return adminGroupOldMapper.selectAdminPrivilegeTotalCount(param);
	}

	public void getAdminPrivilege(Model model, String uid) {
		List<AdminMenu> menuList = adminMenuMapper.selectMenuListAll();
		List<ResponseMenuDto> responseMenuList = ResponseMenuDto.toDtoList(menuList);

		if (StringUtils.hasText(uid)) {
			try {
				AdminGroup adminGroup = this.selectAdminPrivilege(uid);

				Map<Integer, String> permissionMap = new HashMap<>();
				for (AdminGroupPermission permission : adminGroup.getAdminGroupPermissionList()) {
					permissionMap.put(permission.getMenuIndex(), permission.getPermission());
				}

				for (ResponseMenuDto menu : responseMenuList) {
					String permission = permissionMap.getOrDefault(menu.getMenuIndex(), "N");

					menu.setIsCheck(permission.equals("Y") ? "Y" : "N");
				}

				model.addAttribute("adminRole", adminGroup);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<ResponseMenuDto> rootMenus = buildMenuTreeAndFilterByDepth(responseMenuList);
		model.addAttribute("menuList", rootMenus);

	}
	public AdminGroup selectAdminPrivilege(String uid) {
		Optional<AdminGroup> _privilege = adminGroupOldMapper.selectAdminPrivilege(uid);

		if(_privilege.isEmpty()) throw new GeneralException(BAD_REQUEST);

		return _privilege.get();
	}

	public List<ResponseMenuDto> buildMenuTreeAndFilterByDepth(List<ResponseMenuDto> menuList) {
		Map<Integer, ResponseMenuDto> menuMap = new HashMap<>();
		List<ResponseMenuDto> rootMenus = new ArrayList<>();
		for (ResponseMenuDto menu : menuList) {
			menuMap.put(menu.getMenuIndex(), menu);
			if (1 == menu.getDepth()) {
				rootMenus.add(menu);
			}
		}
		for (ResponseMenuDto menu : menuList) {
			if (menu.getParentIndex() != null && menuMap.containsKey(menu.getParentIndex())) {
				ResponseMenuDto parentMenu = menuMap.get(menu.getParentIndex());
				if (parentMenu.getChildren() == null) {
					parentMenu.setChildren(new ArrayList<>());
				}
				parentMenu.getChildren().add(menu);
			}
		}
		return rootMenus;
	}
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<AdminPrivilege> insertAdminPrivilege(AdminPrivilegeReqeust.Insert req) {
		AdminPrivilege adminPrivilege = new AdminPrivilege(req);
		// 그룹 명 중복 확인
		isExistAdminGroupByGroupName(adminPrivilege);

		// 그룹 저장
		this.insertAdminGroup(adminPrivilege);
		// 권한에 맞는 메뉴저장
		this.insertAdminGroupPermission(adminPrivilege);

		return ResponseEntity.ok(adminPrivilege);
	}

	public void isExistAdminGroupByGroupName(AdminPrivilege adminPrivilege) {
		if(adminGroupOldMapper.isExistAdminGroupByGroupName(adminPrivilege))
			throw new IllegalArgumentException("해당 그룹은 중복된 이름입니다. " + adminPrivilege.getGroupName());
	}

	public void isUseAdminGroup(AdminPrivilege adminPrivilege) {
		if(adminGroupOldMapper.isUseAdminGroup(adminPrivilege) && "N".equals(adminPrivilege.getIsUse()))
			throw new GeneralException("해당 권한을 사용하고 있는 계정이 있습니다. <br>관리자 계정 권한 변경 후 노출여부 설정을 다시 시도해주세요");
	}
	@Transactional(rollbackFor = Exception.class)
	public void insertAdminGroup(AdminPrivilege adminPrivilege) {
		AdminGroup adminGroup = AdminGroup.of(adminPrivilege);

		adminGroupOldMapper.insertAdminGroup(adminGroup);
//		adminGroupOldMapper.insertAdminGroupLog(adminGroup);

		adminPrivilege.setAdminGroupIndex(adminGroup.getAdminGroupIndex());
	}

	@Transactional(rollbackFor = Exception.class)
	public void insertAdminGroupPermission(AdminPrivilege adminPrivilege) {
		List<AdminPrivilegeMenu> menus = adminPrivilege.getMenus();
		int adminGroupIndex = adminPrivilege.getAdminGroupIndex();
		Integer adminIndex = adminPrivilege.getAdminIndex();
		String adminId = adminPrivilege.getAdminId();

		adminGroupPerMissionMapper.insertGroupPermissions(menus, adminGroupIndex, adminIndex, adminId);
	}

	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<AdminPrivilege> updateAdminPrivilege(AdminPrivilegeReqeust.Update req) {
		AdminPrivilege adminPrivilege = new AdminPrivilege(req);
		// 그룹 사용자 여부 확인
		isUseAdminGroup(adminPrivilege);
		// 그룹 명 중복 확인
		isExistAdminGroupByGroupName(adminPrivilege);
		// 그룹 수정
		this.updateAdminGroup(adminPrivilege);
		// 권한에 맞는 메뉴 수정
		this.updateAdminGroupPermission(adminPrivilege);

		return ResponseEntity.ok(adminPrivilege);
	}
	@Transactional(rollbackFor = Exception.class)
	public void updateAdminGroup(AdminPrivilege adminPrivilege) {
		AdminGroup adminGroup = AdminGroup.of(adminPrivilege);

		adminGroupOldMapper.updateAdminGroup(adminGroup);
//		adminGroupOldMapper.insertAdminGroupLog(adminGroup);
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateAdminGroupPermission(AdminPrivilege adminPrivilege) {
		List<AdminGroupPermission> permissions = adminGroupPerMissionMapper.selectGroupPermissionList(adminPrivilege.getAdminGroupIndex());

		Map<Integer, String> currentPermissionMap = permissions
			.stream()
			.collect(Collectors.toMap(AdminGroupPermission::getMenuIndex, AdminGroupPermission::getPermission));

		List<AdminPrivilegeMenu> menus= adminPrivilege.getMenus();
		/*
		 * 권한이 변경된 메뉴만 update
		 */
		for (int i = 0; i < menus.size(); i++) {
			Integer menuIndex = menus.get(i).getMenuIndex();
			String permission = menus.get(i).getPermission();
			String currentPermission = currentPermissionMap.get(menuIndex);

			if (currentPermission != null && !currentPermission.equals(permission)) {
				AdminGroupPermission adminGroupPermission = AdminGroupPermission.of(adminPrivilege, menuIndex, permission);
				adminGroupPerMissionMapper.updateGroupPermission(adminGroupPermission);
			}
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Map<String, String>> deleteAdminPrivilege(String uid) {
		AdminGroup adminGroup = this.selectAdminPrivilege(uid);
		adminGroupPerMissionMapper.deleteAdminGroupPermission(adminGroup.getAdminGroupIndex());
		adminGroupOldMapper.deleteAdminGroup(uid);
		Map<String, String> resMap = new HashMap<>();
		resMap.put("msg", "삭제완료");

		return ResponseEntity.ok(resMap);
	}
}
