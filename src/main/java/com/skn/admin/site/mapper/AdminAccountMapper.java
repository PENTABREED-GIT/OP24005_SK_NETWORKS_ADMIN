package com.skn.admin.site.mapper;

import com.skn.admin.site.dto.adminaccount.AdminAccount;
import com.skn.admin.site.dto.adminaccount.request.AdminAccountSearchParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AdminAccountMapper {
	List<AdminAccount> selectAdminAccountList(AdminAccountSearchParam param);
	int selectAdminAccountTotalCount(AdminAccountSearchParam param);
	Optional<AdminAccount> selectAdminAccount(String uid);
	void insertAdminAccount(AdminAccount adminAccount);
	void updateAdminAccount(AdminAccount adminAccount);
	void deleteAdminAccount(String uid);
	boolean checkDuplicateAdminId(String adminId);
	boolean checkDuplicateEmail(String email);
	List<String> selectDepartmentList();
	List<AdminAccount> selectAdminListAll();
	void updateAdminIsSleep(AdminAccount adminAccount);
	List<String> findInactiveAdminUids();
}
