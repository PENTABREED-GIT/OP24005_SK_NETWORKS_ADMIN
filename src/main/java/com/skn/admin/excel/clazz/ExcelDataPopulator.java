package com.skn.admin.excel.clazz;



import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelDataPopulator {
    public void populateHeaderData(
            SXSSFSheet sheet,
            CellStyle headerStyle,
            Map<String, String> headerMap,
            String billYm
    ) {
        int rowIndex = 0;

        Row row = sheet.createRow(rowIndex++);
        List<Cell> headerCells = new ArrayList<>();
        Map<String, Integer> columnWidthMap = getColumnWidthMap(headerMap);

        int columnIndex = 0;

        addRemainingHeaderData(
                sheet,
                headerCells,
                headerStyle,
                headerMap,
                columnWidthMap,
                columnIndex,
                row
        );
    }

    protected void addRemainingHeaderData(
            SXSSFSheet sheet,
            List<Cell> headerCells,
            CellStyle headerStyle,
            Map<String, String> headerMap,
            Map<String, Integer> columnWidthMap,
            int columnIndex,
            Row row
    ) {
        for (String key : headerMap.keySet()) {
            Cell cell = row.createCell(columnIndex);
            cell.setCellValue(headerMap.get(key));
            cell.setCellStyle(headerStyle);
            if(columnWidthMap.containsKey(headerMap.get(key))) {
                sheet.setColumnWidth(columnIndex, columnWidthMap.get(headerMap.get(key)));
            } else {
                sheet.setColumnWidth(columnIndex, 7000);
            }
            headerCells.add(cell);
            columnIndex++;
        }
    }


    public <T> void populateBodyData(
            SXSSFSheet sheet,
            CellStyle bodyStyle,
            List<T> dataList
    ) {
        int count = 0;
        for (T x : dataList) {
            Row row = sheet.createRow(sheet.getLastRowNum() + 1);
            int columnIndex = 0;



        }
    }

    protected Map<String, Integer> getColumnWidthMap(Map<String, String> headerMap) {
        Map<String, Integer> columnWidthMap = new HashMap<>();
        final int baseWidth = 7000;

        for (String key : headerMap.keySet()) {
            String value = headerMap.get(key);

            // 문자열 길이에 따라 조정할 너비를 계산
            int adjustWidth = (int) (baseWidth + (value.length() * 100));

            // 최대값 제한을 위한 조건문 (예를 들어, 최대 12000으로 제한하고 싶다면)
            // adjustWidth = adjustWidth > 12000 ? 12000 : adjustWidth;

            columnWidthMap.put(value, adjustWidth);
        }
        return columnWidthMap;
    }
}
