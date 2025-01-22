package com.skn.admin.business.service;

import com.skn.admin.business.dto.BusinessArea;
import com.skn.admin.business.dto.BusinessContents;
import com.skn.admin.business.dto.BusinessContentsDetail;
import com.skn.admin.business.dto.request.BusinessContentsSearchParam;
import com.skn.admin.business.mapper.BusinessAreaMapper;
import com.skn.admin.business.mapper.BusinessContentsMapper;
import com.skn.admin.esg.dto.ReportsAndPolicies;
import com.skn.admin.file.dto.FileInfo;
import com.skn.admin.file.service.FileInfoService;
import com.skn.admin.util.NTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessContentsService {

    private final BusinessContentsMapper mapper;
    private final FileInfoService fileInfoService;


    public List<BusinessContents> selectBusinessContentsList(BusinessContentsSearchParam param) {
        return mapper.selectBusinessContentsList(param);
    }

    public int selectBusinessContentsListCount(BusinessContentsSearchParam param) {
        return mapper.selectBusinessContentsListCount(param);
    }

    @Transactional
    public int insertBusinessContents(BusinessContents data) {
        // 기본 정보 insert
        int result = mapper.insertBusinessContents(data);
        if(result <= 0)
            return result;

        // 부가 정보 insert
        for (BusinessContentsDetail detail : data.getBusinessContentsDetailList()) {
            detail.setBusinessContentsIndex(data.getBusinessContentsIndex());
            this.insertBusinessContentsDetail(detail);
        }

        return result;
    }

    public BusinessContents selectBusinessContents(String uid) {
        BusinessContents data = mapper.selectBusinessContents(uid);
        return data;
    }

    public int updateBusinessContents(BusinessContents data) {
        // 기본정보
        int result = mapper.updateBusinessContents(data);
        if(result < 1)
            return result;
        Integer businessContentsIndex = data.getBusinessContentsIndex();

        List<String> detailUidList = new ArrayList<>();    // 수정될 목록. 해당 목록 없는 것들은 삭제 처리
        // 부가 정보 insert or update
        for (BusinessContentsDetail detail : data.getBusinessContentsDetailList()) {
            if(!"".equals(NTUtil.isNull(detail.getBusinessContentsDetailIndex(), ""))) { // update
                mapper.updateBusinessContentsDetail(detail);
            } else { // insert
                detail.setBusinessContentsIndex(businessContentsIndex);
                this.insertBusinessContentsDetail(detail);
            }
            detailUidList.add(detail.getUid());
        }

        // 삭제된 부가 정보 처리
        Map<String, Object> deleteParam = new HashMap<String, Object>();
        deleteParam.put("businessContentsIndex", businessContentsIndex);
        deleteParam.put("detailUidList", detailUidList);

        // 삭제될 부가정보 uid list
        List<String> uidList = mapper.selectBusinessContentsDetailUidList(deleteParam);
        if(uidList.size() < 1)
            return result;

        // 부가 정보 삭제
        this.deleteBusinessContentsDetail(deleteParam);

        return 1;
    }

    public void insertBusinessContentsDetail(BusinessContentsDetail detail) {
        mapper.insertBusinessContentsDetail(detail);

        // file info > update parent index
        FileInfo f = new FileInfo();
        f.setParentUid(detail.getUid());
        f.setParentIndex(detail.getBusinessContentsDetailIndex().toString());
        fileInfoService.updateFileInfo(f);
    }

    @Transactional
    public void deleteBusinessContents(String uid) {
        // 기본 정보
        BusinessContents data = mapper.selectBusinessContents(uid);
        
        // 부가 정보 삭제
        Map<String, Object> deleteParam = new HashMap<String, Object>();
        deleteParam.put("businessContentsIndex", data.getBusinessContentsIndex());
        this.deleteBusinessContentsDetail(deleteParam);
        
        // 기본 정보 삭제
        int result = mapper.deleteBusinessContents(data);
    }

    public List<BusinessContentsDetail> selectBusinessContentsDetail(String businessContentsIndex) {
        return mapper.selectBusinessContentsDetail(businessContentsIndex);
    }

    /**
     *
     * @param deleteParam String businessContentsIndex, [List<String> uidList]
     */
    public void deleteBusinessContentsDetail(Map deleteParam) {
        // 삭제될 부가정보 uid list
        List<String> uidList = mapper.selectBusinessContentsDetailUidList(deleteParam);
        if(uidList.size() < 1)
            return;

        // 부가 정보 삭제
        mapper.deleteBusinessContentsDetail(deleteParam);

        // 부가 정보 파일 삭제
        uidList.forEach(uid-> {
            // 부가정보 파일 삭제
            Map<String, String> map = new HashMap<>();
            map.put("parentUid", uid);
            map.put("parentTable", "BUSINESS_CONTENTS_DETAIL");

            try {
                fileInfoService.deleteFile(map);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

}
