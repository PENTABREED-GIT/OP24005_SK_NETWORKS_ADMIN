package com.skn.admin.excel.clazz;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ExcelConfigurator {

    public SXSSFWorkbook createWorkBook() {
        return new SXSSFWorkbook();
    }

    public SXSSFSheet createSheet(SXSSFWorkbook workbook, String sheetName) {
        return workbook.createSheet(sheetName);
    }

    public CellStyle createHeaderStyle(SXSSFWorkbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();

        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        return headerStyle;
    }

    public CellStyle createBodyStyle(SXSSFWorkbook workbook) {
        CellStyle bodyStyle = workbook.createCellStyle();

        bodyStyle.setAlignment(HorizontalAlignment.RIGHT);
        bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        return bodyStyle;
    }

}
