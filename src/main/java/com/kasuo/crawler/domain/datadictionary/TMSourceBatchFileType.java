package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;


public class TMSourceBatchFileType
        extends DataDictionary<TMSourceBatchFileType> {
    private static final long serialVersionUID = 1L;
    public static final String REJECTED_BT_EXPORT = "1";
    public static final String REJECTED_BT_WEB = "2";

    public TMSourceBatchFileType() {
    }

    public TMSourceBatchFileType(String typeid) {
        setId(typeid);
    }

    public TMSourceBatchFileType(Item item) {
        load(item);
    }


    public boolean isRejectedBTExport() {
        return isType("1");
    }


    public boolean isRejectedBTWeb() {
        return isType("2");
    }
}
