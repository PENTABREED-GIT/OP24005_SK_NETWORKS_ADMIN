package com.skn.admin.excel.controller;

import com.skn.admin.config.annotation.Operation;
import com.skn.admin.config.api.apidto.APIDataResponse;
import com.skn.admin.environment.dto.Admin;
import com.skn.admin.excel.service.ExcelService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/excel")
@Slf4j
public class ExcelApiController {

    private final ExcelService excelService;


    @Operation("엑셀 다운로드")
    @PostMapping("")
    public APIDataResponse<String> excelDownload(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody Map<String, Object> reqMap
    ) {
        excelService.excelDownload(request, response, reqMap);

        return APIDataResponse.of(Boolean.toString(true));
    }

    @PostMapping("/upload")
    public APIDataResponse<String> uploadExcelFile(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request
    ) {
        Admin admin = (Admin) request.getSession().getAttribute("ADMIN");
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            return APIDataResponse.of(Boolean.toString(false));
        } catch (IOException e) {
            return APIDataResponse.of(Boolean.toString(true));
        }
    }

}
