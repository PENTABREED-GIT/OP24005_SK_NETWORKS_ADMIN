package com.skn.admin.environment.mapper;

import com.skn.admin.environment.dto.AdminActionLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminActionLogMapper {
    List selectActionLogList(Map<String, Object> map);
    int selectActionLogCount(Map<String, Object> map);
    int insertAdminActionLog(AdminActionLog adminActionLog);

    @Select("SELECT DOWNLOAD_REASON FROM ADMIN_ACTION_LOG WHERE UID = #{uid}")
    String selectDownloadReasonByUid(String uid);
}
