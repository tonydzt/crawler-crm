package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class SourceBatchStatus extends DataDictionary<SourceBatchStatus> {
    private static final long serialVersionUID = 1L;
    public static final String INIT = "0";
    public static final String REJECTED_PARSING = "1";
    public static final String REJECTED_PARSED = "2";
    public static final String QUOTA_PARSING = "3";
    public static final String QUOTA_PARSED = "4";
    public static final String CRAWLING_TM = "5";
    public static final String CRAWLED_TM = "6";
    public static final String CRAWLING_CU = "7";
    public static final String CRAWLED_CU = "8";

    public SourceBatchStatus() {
    }

    public SourceBatchStatus(String typeid) {
        setId(typeid);
    }

    public SourceBatchStatus(Item item) {
        load(item);
    }


    public boolean isInit() {
        return isType("0");
    }

    public boolean isRejectedParsing() {
        return isType("1");
    }

    public boolean isRejectedParsed() {
        return isType("2");
    }

    public boolean isQuotaParsing() {
        return isType("3");
    }

    public boolean isQuotaParsed() {
        return isType("4");
    }

    public boolean isCrawlingTM() {
        return isType("5");
    }

    public boolean isCrawledTM() {
        return isType("6");
    }

    public boolean isCrawlingCU() {
        return isType("7");
    }

    public boolean isCrawledCU() {
        return isType("8");
    }
}
