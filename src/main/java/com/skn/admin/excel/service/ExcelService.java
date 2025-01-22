package com.skn.admin.excel.service;

import com.skn.admin.environment.dto.Admin;
import com.skn.admin.excel.clazz.ExcelConfigurator;
import com.skn.admin.excel.clazz.ExcelDownloader;
import com.skn.admin.excel.clazz.ExcelbillPopulator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExcelService {



    @Transactional(rollbackFor = Exception.class)
    public void excelDownload(
            HttpServletRequest request,
            HttpServletResponse response,
            Map<String, Object> reqMap
    ) {
        try {
            String excelContentsType = reqMap.get("filename").toString();
            String billYm = "2021-09"; // "2021-09
            String school = reqMap.getOrDefault("clazz", "").toString();
            String category = reqMap.getOrDefault("type", "").toString();


            reqMap.put("billYm", billYm.replace("-", ""));
            reqMap.put("clazz", school);
            reqMap.put("type", category);

            ExcelConfigurator excelConfigurator = new ExcelConfigurator();
            ExcelbillPopulator excelbillPopulator = new ExcelbillPopulator();
            ExcelDownloader excelDownloader = new ExcelDownloader();

            SXSSFWorkbook workbook = excelConfigurator.createWorkBook();
            CellStyle headerStyle = excelConfigurator.createHeaderStyle(workbook);
            CellStyle bodyStyle = excelConfigurator.createBodyStyle(workbook);
            SXSSFSheet sheet = excelConfigurator.createSheet(workbook, reqMap.get("filename").toString());

            excelbillPopulator.populateHeaderData(
                    sheet,
                    headerStyle,
                    (Map<String, String>) reqMap.get("headerMap"),
                    billYm
            );

            if (excelContentsType.equals("QR-List")) {


                excelbillPopulator.populateBodyData(
                        sheet,
                        bodyStyle,
                        null
                );
            }




                excelDownloader.downloadExcel(
                    request,
                    response,
                    workbook,
                    String.format(
                            "%s-%s",
                            reqMap.get("filename").toString(),
                            LocalDate.now().toString()
                    )
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private String getCellStringValue(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return "";
        }

        String cellValue = "";
        switch (cell.getCellType()) {
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    cellValue = dateFormat.format(cell.getDateCellValue());
                } else {
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == Math.floor(numericValue)) {  // 소수점 이하가 없다면 정수로 처리
                        cellValue = String.valueOf((int) numericValue);
                    } else {
                        cellValue = String.valueOf(numericValue);
                    }
                }
                break;
            case BOOLEAN:
                cellValue = Boolean.toString(cell.getBooleanCellValue());
                break;
            case FORMULA:
                cellValue = cell.getCellFormula();
                break;
            case BLANK:
            case _NONE:
            case ERROR:
            default:
                return "";
        }

        // 문자열에서 불필요한 공백, 줄바꿈, 특수 문자 제거
        cellValue = cleanString(cellValue);
        return cellValue;
    }

    private String cleanString(String input) {
        // 공백과 특수 문자 제거 정규식 적용
        input = input.replaceAll("[\\n\\r]+", " ");  // 줄바꿈을 공백으로 변환
        // 일반 공백(' ')을 제외한 다른 모든 공백 문자와 ZWSP(U+200B) 제거
        input = input.replaceAll("[\\p{Z}&&[^ ]|\\u200B]", "");
        input = input.replace("\u200B", "");
        return input;
    }

}
