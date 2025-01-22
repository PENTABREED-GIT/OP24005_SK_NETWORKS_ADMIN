package com.skn.admin.excel.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    public List<String> readRowData(InputStream inputStream) throws Exception {
        Workbook workbook = new XSSFWorkbook(inputStream); // 엑셀 파일 로드
        Sheet sheet = workbook.getSheetAt(0); // 첫 번째 시트
        Row row = sheet.getRow(0); // 첫 번째 행 가져오기

        List<String> rowData = new ArrayList<>();
        if (row != null) {
            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case STRING:
                        rowData.add(cell.getStringCellValue());
                        break;
                    case NUMERIC:
                        rowData.add(String.valueOf(cell.getNumericCellValue()));
                        break;
                    case BOOLEAN:
                        rowData.add(String.valueOf(cell.getBooleanCellValue()));
                        break;
                    case FORMULA:
                        rowData.add(cell.getCellFormula());
                        break;
                    default:
                        rowData.add(" "); // 빈 셀 처리
                }
            }
        }
        workbook.close();
        return rowData; // 행의 모든 셀 데이터를 포함하는 리스트 반환
    }
}
