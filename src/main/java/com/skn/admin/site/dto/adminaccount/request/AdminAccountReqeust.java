package com.skn.admin.site.dto.adminaccount.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class AdminAccountReqeust {
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Insert {
		private String uid;
		private Integer adminGroupIndex;
		private String adminId;
		private String adminPw;
		private String adminName;
		private String phoneNo;
		private String department;
		private String duty;
		private String email;
		private String adminType;
		private String isUse;
		private Integer regAdminIndex;
		private String regAdminId;

		public void setAdmin(Integer regAdminIndex, String regAdminId) {
			this.regAdminIndex = regAdminIndex;
			this.regAdminId = regAdminId;
		}
	}

	@Getter
	@NoArgsConstructor(access= AccessLevel.PROTECTED)
	@AllArgsConstructor(access= AccessLevel.PROTECTED)
	public static class Update {
		private String uid;
		private Integer adminGroupIndex;
		private String adminId;
		private String adminPw;
		private String adminName;
		private String phoneNo;
		private String department;
		private String duty;
		private String email;
		private String adminType;
		private String isUse;
		private Integer regAdminIndex;
		private String regAdminId;

		public void setAdmin(Integer regAdminIndex, String regAdminId) {
			this.regAdminIndex = regAdminIndex;
			this.regAdminId = regAdminId;
		}
	}
}
