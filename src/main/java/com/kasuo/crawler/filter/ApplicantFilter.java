package com.kasuo.crawler.filter;

import com.kasuo.crawler.domain.source.SourceUtil;
import com.sun.tools.javac.util.Filter;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * @author douzhitong
 * @date 2018/11/19
 */
public class ApplicantFilter implements Filter<HSSFRow> {

    private static final int APPLICANT_COLUMN = 5;
    private static final int ADDRESS_COLUMN = 6;

    @Override
    public boolean accepts(HSSFRow hssfRow) {
        String applicant = hssfRow.getCell(APPLICANT_COLUMN).getStringCellValue();
        String address = hssfRow.getCell(ADDRESS_COLUMN).getStringCellValue();
        return SourceUtil.checkValidSourceOrgName(applicant, address);
    }
}
