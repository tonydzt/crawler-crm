package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class TMSourceBatchFileErrFlag extends DataDictionary<TMSourceBatchFileErrFlag> {
    private static final long serialVersionUID = 1L;
    public static final String RIGHT = "0";
    public static final String DUPLICATE = "1";
    public static final String FILE_FORMAT_ERROR = "2";
    public static final String FILE_NAME_ERROR = "3";
    public static final String OTHER = "9";

    public TMSourceBatchFileErrFlag() {
    }

    public TMSourceBatchFileErrFlag(String typeid) {
        setId(typeid);
    }

    public TMSourceBatchFileErrFlag(Item item) {
        load(item);
    }


    public boolean isRight() {
        return isType("0");
    }

    public boolean isDuplicate() {
        return isType("1");
    }

    public boolean isFileFormatError() {
        return isType("2");
    }

    public boolean isFileNameError() {
        return isType("3");
    }

    public boolean isOther() {
        return isType("9");
    }
}
