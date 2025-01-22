package com.skn.admin.esg.service;

import com.skn.admin.esg.dto.ReportsAndPolicies;
import com.skn.admin.esg.dto.request.ReportsAndPoliciesSearchParam;
import com.skn.admin.esg.mapper.ReportsAndPoliciesMapper;
import com.skn.admin.file.dto.FileInfo;
import com.skn.admin.file.service.FileInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportsAndPoliciesService {
    private final ReportsAndPoliciesMapper mapper;
    private final FileInfoService fileInfoService;

    public List<ReportsAndPolicies> selectReportsAndPoliciesList(ReportsAndPoliciesSearchParam param) {
        return mapper.selectReportsAndPoliciesList(param);
    }

    public int selectReportsAndPoliciesListCount(ReportsAndPoliciesSearchParam param) {
        return mapper.selectReportsAndPoliciesListCount(param);
    }

    @Transactional
    public int insertReportsAndPolicies(ReportsAndPolicies data) {
        // 기본 정보 insert
        int result = mapper.insertReportsAndPolicies(data);
        if(result > 0) {
            // file info > update parent index
            FileInfo f = new FileInfo();
            f.setParentUid(data.getUid());
            f.setParentIndex(data.getReportsAndPoliciesIndex().toString());
            fileInfoService.updateFileInfo(f);
        }
        return result;
    }

    public ReportsAndPolicies selectReportsAndPolicies(String uid) {
        ReportsAndPolicies data = mapper.selectReportsAndPolicies(uid);
        return data;
    }

    public int updateReportsAndPolicies(ReportsAndPolicies data) {
        int result = mapper.updateReportsAndPolicies(data);
        if(result > 0) {
            // file info > update parent index
            FileInfo f = new FileInfo();
            f.setParentUid(data.getUid());
            f.setParentIndex(data.getReportsAndPoliciesIndex().toString());
            fileInfoService.updateFileInfo(f);
        }
        return result;
    }

    public void deleteReportsAndPolicies(String uid) {
        ReportsAndPolicies data = this.selectReportsAndPolicies(uid);
        int result = mapper.deleteReportsAndPolicies(data);

        if(result < 1)
            return;

        // 파일 삭제
        Map<String, String> map = new HashMap<>();
        map.put("parentUid", uid);
        map.put("parentTable", "REPORTS_AND_POLICIES");

        try {
            fileInfoService.deleteFile(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
