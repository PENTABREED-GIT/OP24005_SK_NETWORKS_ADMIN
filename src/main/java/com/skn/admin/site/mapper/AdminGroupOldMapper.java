package com.skn.admin.site.mapper;

import com.skn.admin.site.dto.AdminGroup;
import com.skn.admin.site.dto.adminprivilege.AdminPrivilege;
import com.skn.admin.site.dto.adminprivilege.request.AdminPrivilegeSearchParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface AdminGroupOldMapper {
    void insertAdminGroup(AdminGroup adminGroup);
//    void insertAdminGroupLog(AdminGroup adminGroup);
    void updateAdminGroup(AdminGroup adminGroup);
    void deleteAdminGroup(String uid);
    List<AdminGroup> selectAdminGroupList(String isUse);
    List<AdminGroup> selectAdminGroupInfos(Map reqMap);
    int selectAdminGroupTotalCount(Map reqMap);
    Optional<AdminGroup> selectAdminGroupInfo(Map reqMap);

    List<AdminGroup> selectAdminPrivilegeList(AdminPrivilegeSearchParam param);
    int selectAdminPrivilegeTotalCount(AdminPrivilegeSearchParam param);
    Optional<AdminGroup> selectAdminPrivilege(String uid);
    boolean isExistAdminGroupByGroupName(AdminPrivilege adminPrivilege);
    Optional<AdminGroup> selectAdminPrivilegeByAdminGroupIndex(Integer adminGroupIndex);

    boolean isUseAdminGroup(AdminPrivilege adminPrivilege);
}
