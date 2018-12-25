package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class ReviewFlag extends DataDictionary<ReviewFlag> {
    private static final long serialVersionUID = 1L;
    public static final String UNKNOWN = "0";
    public static final String REJECTED = "1";
    public static final String PASS = "2";
    public static final String SYS_REJECTED = "3";
    public static final String NOTICE_PASS = "4";
    public static final String NOTICE_REJECTED = "5";
    public static final String NOTICE_MULTI_CLS_PASS = "6";

    public ReviewFlag() {
    }

    public ReviewFlag(String typeid) {
        setId(typeid);
    }

    public ReviewFlag(Item item) {
        load(item);
    }


    public boolean isUnknown() {
        return isType("0");
    }


    public boolean isRejected() {
        return isType("1");
    }


    public boolean isPass() {
        return isType("2");
    }


    public boolean isSysRejected() {
        return isType("3");
    }


    public boolean isNoticePass() {
        return isType("4");
    }


    public boolean isNoticeRejected() {
        return isType("5");
    }


    public boolean isNoticeMultiClsPass() {
        return isType("6");
    }
}
