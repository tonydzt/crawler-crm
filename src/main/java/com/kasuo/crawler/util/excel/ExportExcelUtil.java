package com.kasuo.crawler.util.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author douzhitong
 * @date 2018/12/22
 */
public class ExportExcelUtil {

    public static void export(ExcelData excelData, String path) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFCellStyle cellStyle =workbook.createCellStyle();
        cellStyle.setWrapText(true);
        //水平居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //垂直居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFSheet sheet = workbook.createSheet(excelData.getName());
        exportHead(sheet, excelData.getTitles(), cellStyle);
        exportData(sheet, excelData.getRows(), cellStyle);

        File f = new File(path);

        //如果已有文件，则在导出文件名字 +1序号
        int num = 1;
        while (f.exists()) {
            String[] pathArr = path.split("\\.");
            f = new File(pathArr[0] + num + ".xls");
            num ++;
        }

        if (!f.getParentFile().exists()) {
            f.getParentFile().getParentFile().mkdir();
            f.getParentFile().mkdir();
            f.createNewFile();
        }
        FileOutputStream out = new FileOutputStream(f);
        workbook.write(out);
        out.close();
    }

    private static void exportData(HSSFSheet sheet, List<List<Object>> rows, HSSFCellStyle cellStyle) {

        int rowNum = 1;
        for (List<Object> rowData : rows) {
            HSSFRow row = sheet.createRow(rowNum);
            for (int i = 0; i < rowData.size(); i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(new HSSFRichTextString(Optional.ofNullable(rowData.get(i)).orElse("").toString()));
                sheet.autoSizeColumn(i, true);
            }
            rowNum++;
        }
    }

    private static void exportHead(HSSFSheet sheet, List<String> titles, HSSFCellStyle cellStyle) {
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < titles.size(); i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            HSSFRichTextString text = new HSSFRichTextString(titles.get(i));
            cell.setCellValue(text);
        }
    }
}
