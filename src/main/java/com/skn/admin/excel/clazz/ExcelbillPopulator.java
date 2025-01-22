package com.skn.admin.excel.clazz;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelbillPopulator extends ExcelDataPopulator{

    @Override
    public void populateHeaderData(
            SXSSFSheet sheet,
            CellStyle headerStyle,
            Map<String, String> headerMap,
            String billYm
    ) {
        int rowIndex = 0;


        Row row = sheet.createRow(rowIndex++);
        List<Cell> headerCells = new ArrayList<>();
        Map<String, Integer> columnWidthMap = super.getColumnWidthMap(headerMap);




        addRemainingHeaderData(
                sheet,
                headerCells,
                headerStyle,
                headerMap,
                columnWidthMap,
                0,
                row
        );
    }

    private int headerPlusYearAndMonth(
            SXSSFSheet sheet,
            CellStyle headerStyle,
            String billYm,
            List<Cell> headerCells,
            Row row
    ) {
        String year = billYm.split("-")[0];
        String month = billYm.split("-")[1];

        int columnIndex = 0;
        Cell yearCell = row.createCell(columnIndex);
        yearCell.setCellValue(year + " 년");
        yearCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(columnIndex, 7000);
        headerCells.add(yearCell);
        columnIndex++;

        Cell monthCell = row.createCell(columnIndex);
        monthCell.setCellValue(month + " 월");
        monthCell.setCellStyle(headerStyle);
        sheet.setColumnWidth(columnIndex, 7000);
        headerCells.add(monthCell);
        columnIndex++;


        return columnIndex;
    }

}
