package com.skn.admin.site.dto.adminprivilege;

import com.skn.admin.base.dto.BaseDTO;
import com.skn.admin.site.dto.adminprivilege.request.AdminPrivilegeReqeust;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"menus"})
public class AdminPrivilege extends BaseDTO {
	private Integer adminGroupIndex;
	private String groupName;
	private String isUse;
	private String description;
	private List<AdminPrivilegeMenu> menus;
	private String isSuper;
	private String isMain;

	public AdminPrivilege(AdminPrivilegeReqeust.Insert req) {
		this.uid = RandomStringUtils.randomAlphanumeric(16);
		this.adminGroupIndex = req.getAdminGroupIndex();
		this.groupName = req.getGroupName();
		this.isUse = req.getIsUse();
		this.description = req.getDescription();
		this.menus = req.getMenus();
		this.isSuper = req.getIsSuper();
		this.isMain = req.getIsMain();
		this.adminIndex = req.getAdminIndex();
		this.adminId = req.getAdminId();
	}

	public AdminPrivilege(AdminPrivilegeReqeust.Update req) {
		this.uid = req.getUid();
		this.adminGroupIndex = req.getAdminGroupIndex();
		this.groupName = req.getGroupName();
		this.isUse = req.getIsUse();
		this.description = req.getDescription();
		this.menus = req.getMenus();
		this.isSuper = req.getIsSuper();
		this.isMain = req.getIsMain();
		this.adminIndex = req.getAdminIndex();
		this.adminId = req.getAdminId();
	}
	public void setAdminGroupIndex(Integer adminGroupIndex) {
		this.adminGroupIndex = adminGroupIndex;
	}
}
