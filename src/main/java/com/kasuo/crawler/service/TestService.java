package com.kasuo.crawler.service;

import com.kasuo.crawler.codec.BaituEncoder;
import com.kasuo.crawler.config.CrawlerConfig;
import com.kasuo.crawler.dao.BaituDao;
//import com.kasuo.crawler.dao.TtmDao;
//import com.teamdev.jxbrowser.chromium.JxBrowserDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author douzhitong
 * @date 18/11/15
 */
@Service
public class TestService {

    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    @Autowired
    private CrawlerConfig crawlerConfig;

    @Autowired
    private BaituDao baituDao;

    @Autowired
    private BaituEncoder baituEncoder;

//    @Autowired
//    private TtmDao ttmDao;

    @Autowired
    private BaituTrademarkService baituTrademarkService;

    @PostConstruct
    public void test() throws IOException {
//        logger.debug(crawlerConfig.toString());
//        logger.debug(baituDao.getDecodeMap2().toString());
//        baituEncoder.getInstance();
////        logger.debug(baituEncoder.getRegNum("7600520020004700480040006A002A00"));
////        logger.debug(baituEncoder.getRegNum("7A005200790044004800490067002F00"));
////        logger.debug(baituEncoder.getRegNum("7B0059002100440045004100"));
////        logger.debug(baituEncoder.getRegNum("75005300230049004000480064002800"));
//        logger.debug(ttmDao.find().toString());
//        baituTrademarkService.excel();
    }
}
