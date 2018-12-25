package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class ContactResult extends DataDictionary<ContactResult> {
    private static final long serialVersionUID = 1L;
    public static final String PURPOSE = "1";
    public static final String NO_PURPOSE = "2";
    public static final String REJECTED = "3";
    public static final String BLACKLIST = "4";
    public static final String NO_ANSWER = "5";
    public static final String NOT_AVAILABLE = "6";
    public static final String ERROR_NUMBER = "7";
    public static final String BLANK_NUMBER = "8";
    public static final String OTHER = "9";

    public ContactResult() {
    }

    public ContactResult(String typeid) {
        setId(typeid);
    }

    public ContactResult(Item item) {
        load(item);
    }


    public boolean isPurpose() {
        return isType("1");
    }


    public boolean isNoPurpose() {
        return isType("2");
    }


    public boolean isRejected() {
        return isType("3");
    }


    public boolean isBlacklist() {
        return isType("4");
    }


    public boolean isNoAnswer() {
        return isType("5");
    }


    public boolean isNotAvailable() {
        return isType("6");
    }


    public boolean isErrorNumber() {
        return isType("7");
    }


    public boolean isBlankNumber() {
        return isType("8");
    }


    public boolean isOTHER() {
        return isType("9");
    }
}
