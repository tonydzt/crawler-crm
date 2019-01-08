package com.kasuo.crawler.controller;

import com.kasuo.crawler.common.ErrorCode;
import com.kasuo.crawler.common.Response;
import com.kasuo.crawler.util.SpringBeanUtil;
import com.teamdev.jxbrowser.chromium.JxBrowserDemo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author douzhitong
 * @date 2018/12/20
 */
@RestController
@RequestMapping(value = "/v1/crawler")
public class CrawlerController {

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public Response start() {
        SpringBeanUtil.getBean(JxBrowserDemo.class).start();
        return ErrorCode.OK_EMPTY;
    }

    @RequestMapping(value = "/crawlerDate", method = RequestMethod.POST)
    public Response crawlerDate() {
        return ErrorCode.OK_EMPTY;
    }
}
