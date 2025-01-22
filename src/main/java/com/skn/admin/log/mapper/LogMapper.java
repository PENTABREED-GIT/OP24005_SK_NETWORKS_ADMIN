package com.skn.admin.log.mapper;

import com.skn.admin.account.dto.AdminGroup;
import com.skn.admin.account.dto.AdminGroupPermission;
import com.skn.admin.log.dto.ActionLog;
import com.skn.admin.log.dto.LoginLog;
import com.skn.admin.log.dto.request.ActionLogSearchParam;
import com.skn.admin.log.dto.request.LoginLogSearchParam;
import com.skn.admin.log.dto.response.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LogMapper {

    /*************************************************** READE *******************************************/

    List<ResponseErrorLogDtoList> selectErrorLogList(Map<String, Object> reqMap);

    Integer selectErrorLogListCount(Map<String, Object> reqMap);

    List<LoginLog> selectLoginLogList(LoginLogSearchParam loginLogSearchParam);

    Integer selectLoginLogListCount(LoginLogSearchParam loginLogSearchParam);

    List<ActionLog> selectUseLogList(ActionLogSearchParam actionLogSearchParam);

    Integer selectUseLogListCount(ActionLogSearchParam actionLogSearchParam);

    List<AdminGroup> selectAdminGroupLogList(Map<String, String> searchMap);
    Integer selectAdminGroupLogTotalCount(Map<String, String> searchMap);
    AdminGroup selectAdminGroupLog(Integer adminGroupLogIndex);
    List<AdminGroupPermission> selectAdminGroupPermissionLogList(Integer adminGroupLogIndex);
}
