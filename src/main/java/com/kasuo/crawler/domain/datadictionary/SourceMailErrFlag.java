package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class SourceMailErrFlag extends DataDictionary<SourceMailErrFlag> {
    private static final long serialVersionUID = 1L;
    public static final String RIGHT = "0";
    public static final String MAIL_PARSE_ERROR = "1";
    public static final String MAIL_DUPLICATE = "2";
    public static final String NO_ATTACH = "3";

    public SourceMailErrFlag() {
    }

    public SourceMailErrFlag(String typeid) {
        setId(typeid);
    }

    public SourceMailErrFlag(Item item) {
        load(item);
    }


    public static final String FILE_DOWNLOAD_ERROR = "4";


    public static final String FILE_UNZIP_ERROR = "5";


    public static final String DATA_DUPLICATE = "6";


    public static final String MISS_REJECTED_ERROR = "7";


    public static final String MISS_QUOTATION_ERROR = "8";


    public static final String OTHER = "9";


    public boolean isRight() {
        return isType("0");
    }

    public boolean isMailParseError() {
        return isType("1");
    }

    public boolean isMailDuplicate() {
        return isType("2");
    }

    public boolean isNoAttach() {
        return isType("3");
    }

    public boolean isFileDownloadError() {
        return isType("4");
    }

    public boolean isFileUnzipError() {
        return isType("5");
    }

    public boolean isDataDuplicate() {
        return isType("6");
    }

    public boolean isMissRejectedError() {
        return isType("7");
    }

    public boolean isMissQuotationError() {
        return isType("8");
    }

    public boolean isOther() {
        return isType("9");
    }
}
