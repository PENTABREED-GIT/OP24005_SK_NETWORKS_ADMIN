package com.skn.admin.main.service;

import com.skn.admin.main.mapper.MainMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MainService {
    private final MainMapper mainMapper;

    public Map selectTestList() {
        return mainMapper.selectTestList();
    }
}
