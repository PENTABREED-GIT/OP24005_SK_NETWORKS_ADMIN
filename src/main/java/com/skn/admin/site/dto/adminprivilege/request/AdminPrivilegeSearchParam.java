package com.skn.admin.site.dto.adminprivilege.request;

import com.skn.admin.base.etc.Page;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class AdminPrivilegeSearchParam {
	private String searchValue;
	private String isUse;
	private int size = 30;
	private int offset = 0;

	private AdminPrivilegeSearchParam(
		String searchValue,
		String isUse,
		int size,
		int offset
	) {
		this.searchValue = searchValue;
		this.isUse = isUse;
		this.size = size;
		this.offset = offset;
	}

	public static AdminPrivilegeSearchParam of(AdminPrivilegeSearchParam _param, Page paging) {
		return new AdminPrivilegeSearchParam(
			_param.getSearchValue(),
			_param.getIsUse(),
			paging.getPageListSize(),
			paging.getOffset()
		);
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
		if("전체".equals(isUse)) {
			this.isUse = "";
		}
	}
}
