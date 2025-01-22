package com.skn.admin.business.service;

import com.skn.admin.business.dto.BusinessArea;
import com.skn.admin.business.mapper.BusinessAreaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessAreaService {
    private final BusinessAreaMapper businessAreaMappger;
    public List<BusinessArea> selectBusinessAreaList() {
        return businessAreaMappger.selectBusinessAreaList();
    }
}
