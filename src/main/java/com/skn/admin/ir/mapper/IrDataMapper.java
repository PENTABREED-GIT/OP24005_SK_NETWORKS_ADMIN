package com.skn.admin.ir.mapper;

import com.skn.admin.ir.dto.IrData;
import com.skn.admin.ir.dto.request.IrDataSearchParam;

import java.util.List;

public interface IrDataMapper {
    List<IrData> selectIrDataList(IrDataSearchParam param);
    int selectIrDataListCount(IrDataSearchParam param);
    int insertIrData(IrData data);
    int deleteIrData(IrData data);
    int updateIrData(IrData data);
    IrData selectIrData(String uid);
}
