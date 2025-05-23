package com.skn.admin.util;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NTExcelUtil {

    private int rowNum = 0;
    //File로 만들 경우
    public void createExcelToFile(List<Map<String, Object>> datas, String filepath) throws FileNotFoundException, IOException {
        //workbook = new HSSFWorkbook(); // 엑셀 97 ~ 2003
        //workbook = new XSSFWorkbook(); // 엑셀 2007 버전 이상

        Workbook workbook = new SXSSFWorkbook(); // 성능 개선 버전
        Sheet sheet = workbook.createSheet("데이터");

        rowNum = 0;

//        createExcel(sheet, datas);

        FileOutputStream fos = new FileOutputStream(new File(filepath));
        workbook.write(fos);
        workbook.close();

    }

    //HttpServletResponse 경우
    public void createExcelToResponse(List<Map<String, Object>> datas, String filename, HttpServletResponse response) throws IOException {
        Workbook workbook = new SXSSFWorkbook(); // 성능 개선 버전
        Sheet sheet = workbook.createSheet("데이터");

        rowNum = 0;

//        createExcel(sheet, datas);

        // 컨텐츠 타입과 파일명 지정
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", String.format("attachment;filename=%s.xlsx", filename));

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    //엑셀 생성
    public static void createExcel(Sheet sheet, List<LinkedHashMap<String, Object>> datas, int startRowNum) {
        int rowNum = startRowNum;
        int noNum = 1;

        //데이터를 한개씩 조회해서 한개의 행으로 만든다.
        for (Map<String, Object> data : datas) {
            //row 생성
            Row row = sheet.createRow(rowNum++);

            int cellNum = 0;
            Cell noCell = row.createCell(cellNum);
            noCell.setCellValue(noNum++);
            cellNum++;

            //map에 있는 데이터를 한개씩 조회해서 열을 생성한다.
            for (String key : data.keySet()) {
                if("dataUid".equals(key) || "emailUid".equals(key) || "uid".equals(key)) {
                    continue;
                }


                //cell 생성
                Cell cell = row.createCell(cellNum++);
                String cellValue = NTUtil.isNull(data.get(key),"");
                //cell에 데이터 삽입
                if("email".equals(key) || "toMail".equals(key)) {
                    cellValue =  NTUtil.isNull(NTCryptoUtil.decrypt(cellValue, "EMAIL"),"");
                }
                cell.setCellValue(cellValue);
            }
        }
    }
}
