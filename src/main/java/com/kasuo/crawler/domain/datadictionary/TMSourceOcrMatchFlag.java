package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class TMSourceOcrMatchFlag extends DataDictionary<TMSourceOcrMatchFlag> {
    private static final long serialVersionUID = 1L;
    public static final String SUCCESS = "0";
    public static final String NO_MATCH = "1";
    public static final String ONE_FILENAME_NOT_MATCH = "2";
    public static final String MULTI_FILE_NOT_MATCH = "3";
    public static final String OTHER = "9";

    public TMSourceOcrMatchFlag() {
    }

    public TMSourceOcrMatchFlag(String typeid) {
        setId(typeid);
    }

    public TMSourceOcrMatchFlag(Item item) {
        load(item);
    }


    public boolean isSuccess() {
        return isType("0");
    }

    public boolean isNoMatch() {
        return isType("1");
    }

    public boolean isOneFilenameNotMatch() {
        return isType("2");
    }

    public boolean isMultiFileNotMatch() {
        return isType("3");
    }

    public boolean isOther() {
        return isType("9");
    }
}
