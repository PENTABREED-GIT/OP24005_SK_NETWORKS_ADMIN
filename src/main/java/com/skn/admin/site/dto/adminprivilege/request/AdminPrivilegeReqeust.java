package com.skn.admin.site.dto.adminprivilege.request;

import com.skn.admin.site.dto.adminprivilege.AdminPrivilegeMenu;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class AdminPrivilegeReqeust {
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Insert {
		private Integer adminGroupIndex;
		private String uid;
		private Integer adminIndex;
		private String adminId;
		private String groupName;
		private String isUse;
		private String description;
		private List<AdminPrivilegeMenu> menus;
		private String regDate;
		private String modDate;
		private String isSuper;
		private String isMain;

		public void setAdmin(Integer adminIndex, String adminId) {
			this.adminIndex = adminIndex;
			this.adminId = adminId;
		}
	}

	@Getter
	@NoArgsConstructor(access= AccessLevel.PROTECTED)
	@AllArgsConstructor(access= AccessLevel.PROTECTED)
	public static class Update {
		private Integer adminGroupIndex;
		private String uid;
		private Integer adminIndex;
		private String adminId;
		private String groupName;
		private String isUse;
		private String description;
		private List<AdminPrivilegeMenu> menus;
		private String regDate;
		private String modDate;
		private String isSuper;
		private String isMain;

		public void setAdmin(Integer adminIndex, String adminId) {
			this.adminIndex = adminIndex;
			this.adminId = adminId;
		}
	}
}
