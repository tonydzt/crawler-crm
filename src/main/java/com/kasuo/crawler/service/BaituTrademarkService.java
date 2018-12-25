package com.kasuo.crawler.service;

import com.kasuo.crawler.common.ErrorCode;
import com.kasuo.crawler.common.Response;
import com.kasuo.crawler.config.ExcelConfig;
import com.kasuo.crawler.controller.ExcelController;
import com.kasuo.crawler.dao.TrademarkDao;
import com.kasuo.crawler.dao.mybatis.ExcelStatusDao;
import com.kasuo.crawler.domain.ExcelStatus;
import com.kasuo.crawler.domain.Trademark;
import com.kasuo.crawler.filter.ApplicantFilter;
import com.kasuo.crawler.filter.ResetFilter;
import com.kasuo.crawler.filter.StatusFilter;
import com.kasuo.crawler.filter.UniqueFilter;
import com.sun.tools.javac.util.Filter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author douzhitong
 * @date 2018/11/19
 */
@Service
public class BaituTrademarkService {

    private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);

    private static List<Filter<HSSFRow>> filterList = new ArrayList<>();

    @Autowired
    private ExcelConfig excelConfig;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private ExcelStatusDao excelStatusDao;

    static {
        filterList.add(new StatusFilter());
        filterList.add(new ApplicantFilter());
        filterList.add(new UniqueFilter());
    }

    @Autowired
    private TrademarkDao trademarkDao;

    public Response excel(String path) throws InterruptedException {

        long t1 = System.currentTimeMillis();

        String filePath = excelConfig.getRootPath() + path;
        File dir = new File(filePath);

        if (!dir.exists()) {
            logger.error("No path, fileName: {}", filePath);
            return ErrorCode.ERROR_NO_PATH;
        }

        if (!dir.isDirectory()) {
            logger.error("Not a path, fileName: {}", filePath);
            return ErrorCode.ERROR_NOT_PATH;
        }

        if (dir.listFiles().length > 0) {
            CountDownLatch countDownLatch = new CountDownLatch(dir.listFiles().length);
            for (File file : dir.listFiles()) {
                threadPoolTaskExecutor.submit(() -> {
                    parse(file);
                    countDownLatch.countDown();
                    logger.info("release " + Thread.currentThread().getName() + ":" + String.valueOf(countDownLatch.getCount()));
                });
            }

            countDownLatch.await(10L, TimeUnit.SECONDS);
            long t2 = System.currentTimeMillis();

            logger.info("Excel parse completed! Cost {}ms", t2 -t1);

            return ErrorCode.OK_EMPTY;
        } else {
            return ErrorCode.ERROR_EMPTY_PATH;
        }
    }

    private void parse(File file) {

        ExcelStatus excelStatus = excelStatusDao.findFile(file.getName(), file.getParent());

        if (excelStatus == null || excelStatus.getStatus() == ExcelStatus.UN_PARSE) {

            if (excelStatus == null) {
                excelStatus = new ExcelStatus();
                excelStatus.setPath(file.getParent());
                excelStatus.setFileName(file.getName());
                excelStatus.setStatus(ExcelStatus.PARSING);
                excelStatusDao.insert(excelStatus);
            }

            POIFSFileSystem fs = null;
            HSSFWorkbook wb = null;

            try {
                fs = new POIFSFileSystem(new FileInputStream(file));
                wb = new HSSFWorkbook(fs);
            } catch (IOException e) {
                e.printStackTrace();
            }

            HSSFSheet sheet = wb.getSheetAt(0);
            int rowcount = sheet.getLastRowNum();

            int valid = 0;
            int duplicate = 0;

            for (int i = 0; i < rowcount; i++) {
                HSSFRow row = sheet.getRow(i);
                if (check(row)) {
                    valid++;
                    boolean isDuplicate = save(row, i == rowcount - 1, excelStatus.getId());
                    if (isDuplicate) {
                        duplicate++;
                    }
                }
            }

            clear();

            ExcelStatus updateExcelStatus = new ExcelStatus();
            updateExcelStatus.setId(excelStatus.getId());
            updateExcelStatus.setStatus(ExcelStatus.PARSED);
            updateExcelStatus.setTotalNum(rowcount);
            updateExcelStatus.setValidNum(valid);
            updateExcelStatus.setDuplicateNum(duplicate);
            excelStatusDao.update(updateExcelStatus);
        }
    }

    private void clear() {

        for (Filter<HSSFRow> filter : filterList) {
            if (filter instanceof ResetFilter) {
                ((ResetFilter)filter).reset();
            }
        }
    }

    private boolean save(HSSFRow row, boolean isLast, long excelStatusId) {

        Trademark trademark = new Trademark();
        trademark.setExcelStatusId(excelStatusId);
        trademark.setRegistrationNo(Integer.parseInt(row.getCell(2).getStringCellValue()));
        trademark.setTrademark(row.getCell(3).getStringCellValue());
        trademark.setDate(row.getCell(4).getStringCellValue());
        trademark.setApplicant(row.getCell(5).getStringCellValue());
        trademark.setAddress(row.getCell(6).getStringCellValue());
        trademark.setAgent(row.getCell(7).getStringCellValue());
//        trademark.setService(row.getCell(8).getStringCellValue());
        trademark.setStatus("待审中".equals(row.getCell(8).getStringCellValue()) ? 1 : null);

        return trademarkDao.insert(trademark);
    }

    private boolean check(HSSFRow row) {
        for (Filter<HSSFRow> filter : filterList) {
            if (!filter.accepts(row)) {
                return false;
            }
        }
        return true;
    }

}
