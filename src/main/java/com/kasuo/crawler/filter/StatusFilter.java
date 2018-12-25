package com.kasuo.crawler.filter;

import com.sun.tools.javac.util.Filter;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * @author douzhitong
 * @date 2018/11/19
 */
public class StatusFilter implements Filter<HSSFRow> {

    private static final int STATUS_COLUMN = 8;
    private static final String FALSE_STRING = "已销亡";

    @Override
    public boolean accepts(HSSFRow hssfRow) {
        return !FALSE_STRING.equals(hssfRow.getCell(STATUS_COLUMN).getStringCellValue());
    }
}
