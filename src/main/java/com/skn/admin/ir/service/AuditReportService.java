package com.skn.admin.ir.service;

import com.skn.admin.file.dto.FileInfo;
import com.skn.admin.file.service.FileInfoService;
import com.skn.admin.ir.dto.AuditReport;
import com.skn.admin.ir.dto.request.AuditReportSearchParam;
import com.skn.admin.ir.mapper.AuditReportMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuditReportService {
    private final AuditReportMapper mapper;
    private final FileInfoService fileInfoService;

    public List<AuditReport> selectAuditReportList(AuditReportSearchParam param) {
        return mapper.selectAuditReportList(param);
    }

    public int selectAuditReportListCount(AuditReportSearchParam param) {
        return mapper.selectAuditReportListCount(param);
    }

    @Transactional
    public int insertAuditReport(AuditReport data) {
        // 기본 정보 insert
        int result = mapper.insertAuditReport(data);
        if(result > 0) {
            // file info > update parent index
            FileInfo f = new FileInfo();
            f.setParentUid(data.getUid());
            f.setParentIndex(data.getAuditReportIndex().toString());
            fileInfoService.updateFileInfo(f);
        }

        return result;
    }

    public AuditReport selectAuditReport(String uid) {
        AuditReport data = mapper.selectAuditReport(uid);
        return data;
    }

    public int updateAuditReport(AuditReport data) {
        int result = mapper.updateAuditReport(data);
        if(result > 0) {
            // file info > update parent index
            FileInfo f = new FileInfo();
            f.setParentUid(data.getUid());
            f.setParentIndex(data.getAuditReportIndex().toString());
            fileInfoService.updateFileInfo(f);
        }

        return result;}

    public void deleteAuditReport(String uid) {
        AuditReport data = this.selectAuditReport(uid);
        int result = mapper.deleteAuditReport(data);
        if(result < 1)
            return;

        // 파일 삭제
        Map<String, String> map = new HashMap<>();
        map.put("parentUid", uid);
        map.put("parentTable", "AUDIT_REPORT");

        try {
            fileInfoService.deleteFile(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
