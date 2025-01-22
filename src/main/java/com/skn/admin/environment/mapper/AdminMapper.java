package com.skn.admin.environment.mapper;

import com.skn.admin.environment.dto.Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminMapper {

    int selectAdminIdCount(String adminId);
    int selectAdminEmailCount(String adminId);
    int insertAdmin(Admin admin);
    int updateAdmin(Admin admin);

    Admin selectAdmin(String uid);
    Admin selectAdminById(String id);

    List<Admin> selectAdminList(Map reqMap);

    int selectAdminTotalCount(Map reqMap);

    int insertAdminSession(Map map);
    String selectAdminSessionToken(Map map);
    void deleteAdminSession(Map map);
    void deleteAdmin(String uid);
}
