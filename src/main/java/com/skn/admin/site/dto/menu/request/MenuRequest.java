package com.skn.admin.site.dto.menu.request;

import lombok.*;

@Getter
public class MenuRequest {
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Insert {
		private String uid;
		private String menuName;
		private Integer parentIndex;
		private Integer depth;
		private String menuCode;
		private Integer sortOrder;
		private String url;
		private Integer rootIndex;
		private String isDisplay;
		private String regDate;
		private String modDate;
		private String urlString;
		private String description;
		private String icon;
		private String adminId;
		private Integer adminIndex;

		public void setAdmin(Integer adminIndex, String adminId) {
			this.adminIndex = adminIndex;
			this.adminId = adminId;
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Update {
		private String uid;
		private String menuName;
		private Integer parentIndex;
		private Integer depth;
		private String menuCode;
		private Integer sortOrder;
		private String url;
		private Integer rootIndex;
		private String isDisplay;
		private String regDate;
		private String modDate;
		private String urlString;
		private String description;
		private String icon;
		private String adminId;
		private Integer adminIndex;

		public void setAdmin(Integer adminIndex, String adminId) {
			this.adminIndex = adminIndex;
			this.adminId = adminId;
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class TopMenu {
		private Integer adminGroupIndex;
		private Integer parentIndex;
		private String isDisplay = "Y";
		private String url;
		private TopMenu(Integer adminGroupIndex) {
			this.adminGroupIndex = adminGroupIndex;
		}
		public static TopMenu of(Integer adminGroupIndex) {
			return new TopMenu(adminGroupIndex);
		}
		public static TopMenu of() {
			return new TopMenu();
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	public static class LeftMenu {
		private Integer depth;
		private String isDisplay = "Y";
		private Integer adminGroupIndex;
		private Integer rootIndex;
		private String url;

		private LeftMenu(Integer depth, String url) {
			this.depth = initDepth(depth);
			this.url = url;
		}
		public static LeftMenu of(Integer depth, String url) {
			return new LeftMenu(depth, url);
		}
		private LeftMenu(Integer adminGroupIndex, Integer depth, String url) {
			this.adminGroupIndex = adminGroupIndex;
			this.depth = initDepth(depth);
			this.url = url;
		}
		public static LeftMenu of(Integer adminGroupIndex, Integer depth, String url) {
			return new LeftMenu(adminGroupIndex, depth, url);
		}
		public void setUrl(String url) {
			this.url = url;
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Menu {
		private Integer depth;
		private String url;

		public static Menu of(Integer depth, String url) {
			return new Menu(depth, url);
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}
	private static Integer initDepth(Integer depth) {
		return depth != null && depth > 0 ? depth : 1;
	}
}
