package com.kasuo.crawler.filter;

import com.kasuo.crawler.domain.source.SourceUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * @author douzhitong
 * @date 2018/11/19
 */
public class ApplicantFilter implements Filter<HSSFRow> {

    private static final int APPLICANT_COLUMN = 3;
    private static final int ADDRESS_COLUMN = 6;

    @Override
    public boolean accepts(HSSFRow hssfRow) {
        if (hssfRow.getPhysicalNumberOfCells() > 6) {
            String applicant = hssfRow.getCell(APPLICANT_COLUMN).getStringCellValue();
            String address = hssfRow.getCell(ADDRESS_COLUMN).getStringCellValue();
            return SourceUtil.checkValidSourceOrgName(applicant, address);
        }
        return false;
    }
}
