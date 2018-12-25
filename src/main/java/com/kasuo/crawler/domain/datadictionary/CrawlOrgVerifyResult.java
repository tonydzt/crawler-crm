package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class CrawlOrgVerifyResult extends DataDictionary<CrawlOrgVerifyResult> {
    private static final long serialVersionUID = 1L;
    public static final String START_VERIFY = "0";
    public static final String VERIFYING = "1";
    public static final String PASS = "2";
    public static final String NO_PASS = "3";

    public CrawlOrgVerifyResult() {
    }

    public CrawlOrgVerifyResult(String typeid) {
        setId(typeid);
    }

    public CrawlOrgVerifyResult(Item item) {
        load(item);
    }


    public boolean isVerifying() {
        return isType("1");
    }

    public boolean isPass() {
        return isType("2");
    }

    public boolean isNoPass() {
        return isType("3");
    }
}
