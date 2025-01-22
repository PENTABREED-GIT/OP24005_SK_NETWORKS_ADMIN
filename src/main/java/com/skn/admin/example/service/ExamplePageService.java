package com.skn.admin.example.service;

import com.skn.admin.base.etc.Page;
import com.skn.admin.config.api.constant.ErrorCode;
import com.skn.admin.config.api.exception.GeneralException;
import com.skn.admin.example.mapper.ExampleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExamplePageService {

    private final ExampleMapper exampleMapper;

    /****************************************************** 초기화 **********************************************/
    public void init(Map<String, Object> reqMap) {
    }

    /************************************************** READ List ******************************************/

    @Transactional(readOnly = true)
    public void selectExampleList(
            Map<String, Object> reqMap,
            Page page,
            Model model
    ) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    /************************************************** READ Object ******************************************/

    @Transactional(readOnly = true)
    public void selectExample(
            String uid,
            Model model
    ) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }
}
