package com.skn.admin.environment.mapper;

import com.skn.admin.environment.dto.AdminLoginLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminLoginLogMapper {
    List<AdminLoginLog> selectLoginLogList(Map<String, Object> map);
    void insertLoginLog(AdminLoginLog adminLoginLog);
	int selectLoginLogCount(Map<String, Object> map);
}
