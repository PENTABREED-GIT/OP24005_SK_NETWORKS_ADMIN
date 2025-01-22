package com.skn.admin.site.mapper;

import com.skn.admin.site.dto.AdminMenu;
import com.skn.admin.site.dto.menu.request.MenuRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AdminMenuMapper {
	List<AdminMenu> selectMenuListAll();
	void insertMenu(AdminMenu menu);
	void updateMenu(AdminMenu menu);
	void deleteMenu(String uid);
	Optional<AdminMenu> selectMenu(String uid);

	List<AdminMenu> selectTopMenuList(MenuRequest.TopMenu topMenu);

	Optional<AdminMenu> selectAdminMenu(MenuRequest.Menu menu);

	List<AdminMenu> selectLeftMenuList(MenuRequest.LeftMenu topMenu);

	boolean isExistByAdminGroupIndexAndUrl(Integer adminGroupIndex, String url);
	String selectMenuUrlsByPermission(Integer adminGroupIndex);
}
