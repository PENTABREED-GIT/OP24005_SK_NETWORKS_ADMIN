package com.skn.admin.account.mapper;

import com.skn.admin.account.dto.AdminGroup;
import com.skn.admin.account.dto.AdminGroupPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminGroupMapper {
    /**
     * 관리자 그룹 목록
     * @param adminGroupSearchParam
     * @return
     */
    List<AdminGroup> selectAdminGroupList(AdminGroup.AdminGroupSearchParam adminGroupSearchParam);

    /**
     * 관리자 그룹 목록 전체 개수
     * @param adminGroupSearchParam
     * @return
     */
    int selectAdminGroupTotalCount(AdminGroup.AdminGroupSearchParam adminGroupSearchParam);

    /**
     * 관리자 그룹 상세 조회
     *
     * @param uid
     * @return
     */
    AdminGroup selectAdminGroup(String uid);

    /**
     * 관리자 그룹 전체 권한 목록
     * @param uid ADMIN_GROUP.UID
     * @return
     */
    List<AdminGroupPermission> selectAdminGroupPermissionList(Integer adminGroupIndex);

    /**
     * 관리자 메뉴 전체 목록(IS_DISPLAY = 'Y' 인 것만)
     * <pre>
     * - 관리자 그룹 등록 시 사용
     * </pre>
     * @return
     */
    List<AdminGroupPermission> selectAdminMenuListAllForGroup();

    /**
     * 관리자 그룹 INSERT
     * @param adminGroup
     * @return
     */
    int insertAdminGroup(AdminGroup adminGroup);

    int insertAdminGroupPermission(AdminGroup adminGroup);
    /**
     * 관리자 그룹 UPDATE
     * @param adminGroup
     * @return
     */
    int updateAdminGroup(AdminGroup adminGroup);

    /**
     * 관리자 그룹 로그 INSERT
     * @param adminGroup
     * @return
     */
    int insertAdminGroupLog(AdminGroup adminGroup);

    /**
     * 관리자 그룹 권한 로그 INSERT
     * @param adminGroup
     * @return
     */
    int insertAdminGroupPermissionLog(AdminGroup adminGroup);
}
