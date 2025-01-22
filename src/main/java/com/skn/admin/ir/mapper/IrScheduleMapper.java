package com.skn.admin.ir.mapper;

import com.skn.admin.ir.dto.IrSchedule;
import com.skn.admin.ir.dto.request.IrScheduleSearchParam;

import java.util.List;

public interface IrScheduleMapper {
    List<IrSchedule> selectIrScheduleList(IrScheduleSearchParam param);
    int selectIrScheduleListCount(IrScheduleSearchParam param);
    int insertIrSchedule(IrSchedule data);
    int deleteIrSchedule(IrSchedule data);
    int updateIrSchedule(IrSchedule data);
    IrSchedule selectIrSchedule(String uid);
}
