package com.skn.admin.excel.clazz;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.OutputStream;

public class ExcelDownloader {


    public void downloadExcel(
            HttpServletRequest request,
            HttpServletResponse response,
            SXSSFWorkbook workbook,
            String filename
    ) throws Exception {
        response.setHeader("Content-Disposition", String.format("attachment;filename=%s.xlsx", filename));
        response.setContentType("application/vnd.ms-excel");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    public Mono<Void> downloadExcelStream(
            HttpServletResponse response,
            SXSSFWorkbook workbook,
            String filename
    ) {
        return Mono.fromRunnable(() -> {
            try {
                response.setHeader("Content-Disposition", String.format("attachment;filename=%s.xlsx", filename));
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                OutputStream outputStream = response.getOutputStream();
                workbook.write(outputStream);
                outputStream.close();
                workbook.close();
            } catch (IOException e) {
                throw new RuntimeException("Error writing Excel to OutputStream", e);
            }
        });
    }
}
