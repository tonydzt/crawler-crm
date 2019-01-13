package com.kasuo.crawler.controller;

import com.kasuo.crawler.common.ErrorCode;
import com.kasuo.crawler.common.Response;
import com.kasuo.crawler.domain.CrawlerConfig;
import com.kasuo.crawler.service.CrawlerConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
    public Map<String,String> queryCrawlerDate() {
        String date = crawlerConfigService.getValueBykey(CrawlerConfig.CRAWLER_DATE);
        String num = crawlerConfigService.getValueBykey(CrawlerConfig.CRAWLER_NUM);

        Map<String,String> result = new HashMap<>();
        result.put(CrawlerConfig.CRAWLER_DATE, date);
        result.put(CrawlerConfig.CRAWLER_NUM, num);
        return result;
    }

    @RequestMapping(value = "/saveCrawlerDate", method = RequestMethod.POST)
    public Response saveCrawlerDate(@RequestBody Map<String,Object> map) {

        String date = (String) map.get(CrawlerConfig.CRAWLER_DATE);
        String num = (String) map.get(CrawlerConfig.CRAWLER_NUM);

        if (!StringUtils.isEmpty(date)) {
            crawlerConfigService.saveCrawlerDate(date);
        }

        if (!StringUtils.isEmpty(num)) {
            crawlerConfigService.saveCrawlerNum(num);
        }
        return ErrorCode.OK_EMPTY;
    }
}
