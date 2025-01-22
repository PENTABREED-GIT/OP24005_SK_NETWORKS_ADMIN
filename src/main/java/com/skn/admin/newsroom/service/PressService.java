package com.skn.admin.newsroom.service;

import com.skn.admin.business.dto.BusinessArea;
import com.skn.admin.file.dto.FileInfo;
import com.skn.admin.file.service.FileInfoService;
import com.skn.admin.newsroom.dto.Press;
import com.skn.admin.newsroom.dto.request.PressSearchParam;
import com.skn.admin.newsroom.mapper.PressMapper;
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
public class PressService {
    private final PressMapper mapper;
    private final FileInfoService fileInfoService;

    public List<Press> selectPressList(PressSearchParam param) {
        return mapper.selectPressList(param);
    }

    public int selectPressListCount(PressSearchParam param) {
        return mapper.selectPressListCount(param);
    }

    @Transactional
    public int insertPress(Press data) {
        // 기본 정보 insert
        int result = mapper.insertPress(data);
        if(result > 0) {
            // file info > update parent index
            FileInfo f = new FileInfo();
            f.setParentUid(data.getUid());
            f.setParentIndex(data.getPressIndex().toString());
            fileInfoService.updateFileInfo(f);
        }
        return result;
    }

    public Press selectPress(String uid) {
        Press data = mapper.selectPress(uid);
        return data;
    }

    public int updatePress(Press data) {
        int result = mapper.updatePress(data);
        if(result > 0) {
            // file info > update parent index
            FileInfo f = new FileInfo();
            f.setParentUid(data.getUid());
            f.setParentIndex(data.getPressIndex().toString());
            fileInfoService.updateFileInfo(f);
        }
        return result;
    }

    public void deletePress(String uid) {
        Press data = this.selectPress(uid);
        int result = mapper.deletePress(data);
        if(result < 1)
            return;

        // 파일 삭제
        Map<String, String> map = new HashMap<>();
        map.put("parentUid", uid);
        map.put("parentTable", "PRESS");

        try {
            fileInfoService.deleteFile(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
