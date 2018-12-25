package com.kasuo.crawler.filter;

import org.apache.poi.hssf.usermodel.HSSFRow;

import java.util.ArrayList;
import java.util.List;

/**
 * @author douzhitong
 * @date 2018/11/20
 */
public class UniqueFilter implements ResetFilter<HSSFRow> {

    private static final int TRADEMARK_COLUMN = 2;
    private static List<Integer> trademarkList = new ArrayList<>();

    @Override
    public boolean accepts(HSSFRow cells) {
        int trademark = Integer.parseInt(cells.getCell(TRADEMARK_COLUMN).getStringCellValue());
        if (trademarkList.contains(trademark)) {
            return false;
        } else {
            trademarkList.add(trademark);
            return true;
        }
    }

    @Override
    public void reset() {
        trademarkList.clear();
    }
}
