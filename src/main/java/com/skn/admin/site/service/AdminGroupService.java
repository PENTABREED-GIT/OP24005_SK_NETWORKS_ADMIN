package com.skn.admin.site.service;

import com.skn.admin.config.api.constant.ErrorCode;
import com.skn.admin.config.api.exception.GeneralException;
import com.skn.admin.site.dto.AdminGroup;
import com.skn.admin.environment.dto.AdminGroupMenuFormParam;
import com.skn.admin.site.mapper.AdminGroupOldMapper;
import com.skn.admin.site.mapper.AdminGroupPermissionMapper;
import com.skn.admin.util.NTResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminGroupService {

    private final AdminGroupOldMapper adminGroupOldMapper;
    private final AdminGroupPermissionMapper adminGroupPermissionMapper;

    public void saveAdminGroup(AdminGroup adminGroup) {
        NTResult ntResult = new NTResult();
        Map<String, Object> map = new HashMap<>();
        map.put("uid", adminGroup.getUid());
        try {
            adminGroupOldMapper.insertAdminGroup(adminGroup);
            ntResult.setSuccess();
            ntResult.setData(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void modifyAdminGroup(
            AdminGroup adminGroup,
            AdminGroupMenuFormParam adminGroupMenuFormParam
    ) {
        adminGroupMenuFormParam.setAdminId(adminGroup.getAdminId());
        adminGroupMenuFormParam.setAdminIndex(adminGroup.getAdminIndex());
        try {
            adminGroupOldMapper.updateAdminGroup(adminGroup);
            adminGroupPermissionMapper.deleteAdminGroupPermission(adminGroupMenuFormParam.getAdminGroupIndex());
            adminGroupPermissionMapper.insertAdminGroupPermission(adminGroupMenuFormParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteAdminGroup(
            AdminGroup adminGroupInfo,
            AdminGroupMenuFormParam adminGroupMenuFormParam
    ) {

        try {
            adminGroupOldMapper.deleteAdminGroup(adminGroupInfo.getUid());
            adminGroupPermissionMapper.deleteAdminGroupPermission(adminGroupMenuFormParam.getAdminGroupIndex());
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR,
                    ErrorCode.DATA_ACCESS_ERROR.getMessage("delete AdminGroup failed"));
        }
    }
    public AdminGroup selectAdminGroupInfo(String uid) {
        Map map = new HashMap<>();
        map.put("uid", uid);
        Optional<AdminGroup> adminGroup = adminGroupOldMapper.selectAdminGroupInfo(map);

        return adminGroup.get();
    }

    public List<AdminGroup> selectAdminGroupInfoList(Map reqMap) {
        return adminGroupOldMapper.selectAdminGroupInfos(reqMap);
    }

    public int selectAdminGroupTotalCount(Map reqMap) {
        return adminGroupOldMapper.selectAdminGroupTotalCount(reqMap);
    }
}
