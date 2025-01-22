package com.skn.admin.ir.mapper;

import com.skn.admin.ir.dto.AuditReport;
import com.skn.admin.ir.dto.request.AuditReportSearchParam;

import java.util.List;

public interface AuditReportMapper {
    List<AuditReport> selectAuditReportList(AuditReportSearchParam param);
    int selectAuditReportListCount(AuditReportSearchParam param);
    int insertAuditReport(AuditReport data);
    int deleteAuditReport(AuditReport data);
    int updateAuditReport(AuditReport data);
    AuditReport selectAuditReport(String uid);
}
