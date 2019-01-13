package com.kasuo.crawler.controller;

import com.kasuo.crawler.common.Response;
import com.kasuo.crawler.service.BaituTrademarkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author douzhitong
 * @date 2018/12/20
 */
@RestController
@RequestMapping(value = "/v1/excel")
public class ExcelController {

    private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);

    @Autowired
    private BaituTrademarkService baituTrademarkService;

    @RequestMapping(value = "/parse", method = RequestMethod.POST)
    public Response excel(@RequestBody Map<String,Object> body) throws InterruptedException {
        String path = body.get("path").toString();
        return baituTrademarkService.excel(path, false);
    }
}
