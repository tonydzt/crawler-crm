package com.kasuo.crawler.service;

import com.kasuo.crawler.dao.mybatis.CrawlerConfigDao;
import com.kasuo.crawler.domain.CrawlerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author douzhitong
 * @date 2019/1/8
 */
@Service
public class CrawlerConfigService {

    @Autowired
    private CrawlerConfigDao crawlerConfigDao;

    public String getValueBykey(String key) {
        CrawlerConfig crawlerConfig = crawlerConfigDao.findByKey(key);
        if (crawlerConfig == null) {
            return "";
        }
        return crawlerConfig.getValue();
    }

    public void saveCrawlerDate(String value) {
        CrawlerConfig crawlerConfig = crawlerConfigDao.findByKey(CrawlerConfig.CRAWLER_DATE);

        boolean needInit = crawlerConfig == null;

        if (needInit) {
            crawlerConfig = new CrawlerConfig();
        }

        crawlerConfig.setKey(CrawlerConfig.CRAWLER_DATE);
        crawlerConfig.setValue(value);

        if (needInit) {
            crawlerConfigDao.insert(crawlerConfig);
        } else {
            crawlerConfigDao.update(crawlerConfig);
        }
    }
}
