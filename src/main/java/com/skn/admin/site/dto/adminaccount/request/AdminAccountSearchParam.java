package com.skn.admin.site.dto.adminaccount.request;

import com.skn.admin.base.dto.SearchParam;
import com.skn.admin.base.etc.Page;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class AdminAccountSearchParam extends SearchParam {
	private String searchValue;
	private String department;
	private String adminGroupIndex;
	private String isUse;
	private int size = 30;
	private int offset = 0;

	private AdminAccountSearchParam(
			LocalDate sDate,
			LocalDate eDate,
			String searchValue,
			String department,
			String adminGroupIndex,
			String isUse,
			int size,
			int offset
	) {
		super(initStartDate(sDate),initEndDate(eDate));
		this.searchValue = searchValue;
		this.department = department;
		this.adminGroupIndex = adminGroupIndex;
		this.isUse = isUse;
		this.size = size;
		this.offset = offset;
	}

	public static AdminAccountSearchParam of(AdminAccountSearchParam _param, Page paging) {
		return new AdminAccountSearchParam(
				_param.getStartDate(),
				_param.getEndDate(),
				_param.getSearchValue(),
				_param.getDepartment(),
				_param.getAdminGroupIndex(),
				_param.getIsUse(),
				paging.getPageListSize(),
				paging.getOffset()
		);
	}

	private static LocalDate initStartDate(LocalDate sDate) {
		return sDate != null ? sDate : LocalDate.now().minusMonths(1);
	}

	private static LocalDate initEndDate(LocalDate eDate) {
		return eDate != null ? eDate : LocalDate.now();
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
		if("전체".equals(isUse)) {
			this.isUse = "";
		}
	}
	public void setDepartment(String department) {
		this.department = department;
		if("전체".equals(department)) {
			this.department = "";
		}
	}
	public void setAdminGroupIndex(String adminGroupIndex) {
		this.adminGroupIndex = adminGroupIndex;
		if("전체".equals(adminGroupIndex)) {
			this.adminGroupIndex = "";
		}
	}
}
