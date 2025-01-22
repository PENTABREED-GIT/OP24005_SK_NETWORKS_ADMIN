package com.skn.admin.account.service;

import com.skn.admin.account.dto.AdminGroup;
import com.skn.admin.account.dto.AdminGroupPermission;
import com.skn.admin.account.mapper.AdminGroupMapper;
import com.skn.admin.site.dto.AdminMenu;
import com.skn.admin.site.mapper.AdminMenuMapper;
import com.skn.admin.util.NTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service("ADMIN_GROUP_SERVICE")
@RequiredArgsConstructor
public class AdminGroupService {
    private final AdminGroupMapper adminGroupMapper;
    private final AdminMenuMapper adminMenuMapper;

    public List<AdminGroup> getAdminGroupList(AdminGroup.AdminGroupSearchParam adminGroupSearchParam) {
        return adminGroupMapper.selectAdminGroupList(adminGroupSearchParam);
    }
    public int getAdminGroupTotalCount(AdminGroup.AdminGroupSearchParam adminGroupSearchParam) {
        return adminGroupMapper.selectAdminGroupTotalCount(adminGroupSearchParam);
    }

    public AdminGroup getAdminGroup(String uid) {
        AdminGroup adminGroup = adminGroupMapper.selectAdminGroup(uid);
        adminGroup.setAdminGroupPermissionList(this.getAdminGroupPermissionList(adminGroup.getAdminGroupIndex()));
        return adminGroup;
    }
    /**
     * 관리자 그룹 권한 목록 조회.
     * 관리자가 지정되어 있지 않은 경우에는 전체 메뉴 목록 조회
     *
     * @param adminGroupIndex
     * @return
     */
    public List<AdminGroupPermission> getAdminGroupPermissionList(Integer adminGroupIndex) {
        if (NTUtil.isEmpty(adminGroupIndex)) {
            return adminGroupMapper.selectAdminMenuListAllForGroup();
        } else {
            return adminGroupMapper.selectAdminGroupPermissionList(adminGroupIndex);
        }
    }

    /**
     * 관리자 그룹 등록
     * 
     * @param adminGroup
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<AdminGroup> registerAdminGroup(AdminGroup adminGroup) {
        adminGroup.setUid(RandomStringUtils.randomAlphanumeric(16));
        adminGroup.setIsSuper("N");
        adminGroup.setIsMain("Y");

        // INSERT ADMIN_GROUP
        adminGroupMapper.insertAdminGroup(adminGroup);

        // INSERT ADMIN_GROUP_PERMISSION
        adminGroupMapper.insertAdminGroupPermission(adminGroup);

        // INSERT ADMIN_GROUP_LOG, ADMIN_GROUP_PERMISSION_LOG
        this.saveAdminGroupLog(adminGroup);

        return ResponseEntity.ok(adminGroup);
    }

    /**
     * 관리자 그룹 수정
     * @param adminGroup
     * @return
     */
    public ResponseEntity<AdminGroup> modifyAdminGroup(AdminGroup adminGroup) {
        adminGroup.setIsSuper("N");
        adminGroup.setIsMain("Y");

        // UPDATE ADMIN GROUP
        adminGroupMapper.updateAdminGroup(adminGroup);

        // INSERT ADMIN_GROUP_PERMISSION
        adminGroupMapper.insertAdminGroupPermission(adminGroup);

        // INSERT ADMIN_GROUP_LOG, ADMIN_GROUP_PERMISSION_LOG
        this.saveAdminGroupLog(adminGroup);

        return ResponseEntity.ok(adminGroup);
    }

    /**
     * 관리자 그룹 권한 로그 저장
     *
     * @param adminGroup
     */
    private void saveAdminGroupLog(AdminGroup adminGroup) {
        adminGroupMapper.insertAdminGroupLog(adminGroup);
        adminGroupMapper.insertAdminGroupPermissionLog(adminGroup);
    }
}
