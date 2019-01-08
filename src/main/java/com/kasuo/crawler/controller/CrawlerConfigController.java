package com.kasuo.crawler.controller;

import com.kasuo.crawler.common.ErrorCode;
import com.kasuo.crawler.common.Response;
import com.kasuo.crawler.domain.CrawlerConfig;
import com.kasuo.crawler.service.CrawlerConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author douzhitong
 * @date 2019/1/8
 */
@RestController
@RequestMapping(value = "/v1/crawlerConfig")
public class CrawlerConfigController {

    @Autowired
    private CrawlerConfigService crawlerConfigService;

    @RequestMapping(value = "/queryCrawlerDate", method = RequestMethod.GET)
    public String queryCrawlerDate() {
        return crawlerConfigService.getValueBykey(CrawlerConfig.CRAWLER_DATE);
    }

    @RequestMapping(value = "/saveCrawlerDate", method = RequestMethod.POST)
    public Response saveCrawlerDate(@RequestBody Map<String,Object> map) {

        String value = (String) map.get(CrawlerConfig.CRAWLER_DATE);
        crawlerConfigService.saveCrawlerDate(value);
        return ErrorCode.OK_EMPTY;
    }
}
