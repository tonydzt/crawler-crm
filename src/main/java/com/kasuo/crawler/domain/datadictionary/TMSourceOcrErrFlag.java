package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class TMSourceOcrErrFlag extends DataDictionary<TMSourceOcrErrFlag> {
    private static final long serialVersionUID = 1L;
    public static final String RIGHT = "0";
    public static final String DUPLICATE = "1";
    public static final String FILE_FORMAT_ERROR = "2";
    public static final String RECOGNIZE_REGNUM_ERROR = "3";
    public static final String RECOGNIZE_CLS_ERROR = "4";
    public static final String QUOTATION_EMPTY = "5";
    public static final String OTHER = "9";

    public TMSourceOcrErrFlag() {
    }

    public TMSourceOcrErrFlag(String typeid) {
        setId(typeid);
    }

    public TMSourceOcrErrFlag(Item item) {
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

    public boolean isRecognizeRegNumError() {
        return isType("3");
    }

    public boolean isRecognizeClsError() {
        return isType("4");
    }

    public boolean isOther() {
        return isType("9");
    }

    public boolean isQuotationEmpty() {
        return isType("5");
    }
}
