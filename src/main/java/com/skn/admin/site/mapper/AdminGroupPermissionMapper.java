package com.skn.admin.site.mapper;

import com.skn.admin.environment.dto.AdminGroupMenuFormParam;
import com.skn.admin.site.dto.AdminGroupPermission;
import com.skn.admin.site.dto.adminprivilege.AdminPrivilegeMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminGroupPermissionMapper {
	int insertAdminGroupPermission(AdminGroupMenuFormParam adminGroupMenuFormParam);
	void deleteAdminGroupPermission(Integer adminGroupIndex);
	void insertAdminGroupPermission2(AdminGroupPermission adminGroupPermission);
	void insertGroupPermissions(List<AdminPrivilegeMenu> permissionDtos, int adminGroupIndex, Integer adminIndex, String adminId);
	void updateGroupPermission(AdminGroupPermission adminGroupPermission);
	List<AdminGroupPermission> selectGroupPermissionList(Integer adminGroupIndex);
}
