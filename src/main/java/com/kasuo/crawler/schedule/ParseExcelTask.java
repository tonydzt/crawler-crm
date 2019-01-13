package com.kasuo.crawler.schedule;

import com.kasuo.crawler.config.ExcelConfig;
import com.kasuo.crawler.dao.mybatis.ExcelStatusDao;
import com.kasuo.crawler.domain.ExcelStatus;
import com.kasuo.crawler.service.BaituTrademarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author douzhitong
 * @date 2019/1/13
 */
@Component
public class ParseExcelTask {

    private static final Set<String> CACHED_FILE_PATH = new HashSet<>();

    @Autowired
    private BaituTrademarkService baituTrademarkService;

    @Autowired
    private ExcelStatusDao excelStatusDao;

    @Autowired
    private ExcelConfig excelConfig;

    @Scheduled(cron = "0 0/10 7-23 * * ?")
    public void autoParseExcel() throws InterruptedException {
        String rootPath = excelConfig.getRootPath();
        File[] fileArr = new File(rootPath).listFiles();
        for (File file : fileArr) {
            String filePath = excelConfig.getRootPath() + file.getName();

            if (file.getName().startsWith("2018") || file.getName().startsWith("2019")) {
                if (CACHED_FILE_PATH.contains(filePath)) {
                    continue;
                }
                List<ExcelStatus> excelStatusList = excelStatusDao.findByPath(filePath);
                if (CollectionUtils.isEmpty(excelStatusList)) {
                    baituTrademarkService.excel(file.getName(), true);
                }
                CACHED_FILE_PATH.add(filePath);
            }
        }
    }
}
