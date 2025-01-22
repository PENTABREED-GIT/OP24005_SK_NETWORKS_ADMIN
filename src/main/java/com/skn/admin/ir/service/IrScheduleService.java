package com.skn.admin.ir.service;

import com.skn.admin.ir.dto.IrSchedule;
import com.skn.admin.ir.dto.request.IrScheduleSearchParam;
import com.skn.admin.ir.mapper.IrScheduleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class IrScheduleService {
    private final IrScheduleMapper mapper;

    public List<IrSchedule> selectIrScheduleList(IrScheduleSearchParam param) {
        return mapper.selectIrScheduleList(param);
    }

    public int selectIrScheduleListCount(IrScheduleSearchParam param) {
        return mapper.selectIrScheduleListCount(param);
    }

    @Transactional
    public int insertIrSchedule(IrSchedule data) {
        // 기본 정보 insert
        int result = mapper.insertIrSchedule(data);
        return result;
    }

    public IrSchedule selectIrSchedule(String uid) {
        IrSchedule data = mapper.selectIrSchedule(uid);
        return data;
    }

    public int updateIrSchedule(IrSchedule data) {
        return mapper.updateIrSchedule(data);
    }

    public void deleteIrSchedule(IrSchedule data) {
        mapper.deleteIrSchedule(data);
    }


}
