package com.skn.admin.ir.service;

import com.skn.admin.esg.dto.ReportsAndPolicies;
import com.skn.admin.file.dto.FileInfo;
import com.skn.admin.file.service.FileInfoService;
import com.skn.admin.ir.dto.IrData;
import com.skn.admin.ir.dto.request.IrDataSearchParam;
import com.skn.admin.ir.mapper.IrDataMapper;
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
public class IrDataService {
    private final IrDataMapper mapper;
    private final FileInfoService fileInfoService;

    public List<IrData> selectIrDataList(IrDataSearchParam param) {
        return mapper.selectIrDataList(param);
    }

    public int selectIrDataListCount(IrDataSearchParam param) {
        return mapper.selectIrDataListCount(param);
    }

    @Transactional
    public int insertIrData(IrData data) {
        // 기본 정보 insert
        int result = mapper.insertIrData(data);

        if(result > 0) {
            // file info > update parent index
            FileInfo f = new FileInfo();
            f.setParentUid(data.getUid());
            f.setParentIndex(data.getIrDataIndex().toString());
            fileInfoService.updateFileInfo(f);
        }

        return result;
    }

    public IrData selectIrData(String uid) {
        IrData data = mapper.selectIrData(uid);
        return data;
    }

    public int updateIrData(IrData data) {
        int result = mapper.updateIrData(data);
        if(result > 0) {
            // file info > update parent index
            FileInfo f = new FileInfo();
            f.setParentUid(data.getUid());
            f.setParentIndex(data.getIrDataIndex().toString());
            fileInfoService.updateFileInfo(f);
        }

        return result;
    }

    public void deleteIrData(String uid) {
        IrData data = this.selectIrData(uid);
        int result = mapper.deleteIrData(data);

        if(result < 1)
            return;

        // 파일 삭제
        Map<String, String> map = new HashMap<>();
        map.put("parentUid", uid);
        map.put("parentTable", "IR_DATA");

        try {
            fileInfoService.deleteFile(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
