package com.kasuo.crawler.dao.mybatis;

import com.kasuo.crawler.domain.CrawlerConfig;
import org.springframework.stereotype.Repository;

/**
 * @author douzhitong
 * @date 2019/1/7
 */
@Repository
public interface CrawlerConfigDao {

    CrawlerConfig findByKey(String key);

    void insert(CrawlerConfig crawlerConfig);

    void update(CrawlerConfig crawlerConfig);
}
