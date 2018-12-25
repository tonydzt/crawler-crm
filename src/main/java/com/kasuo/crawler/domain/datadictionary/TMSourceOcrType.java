package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;


public class TMSourceOcrType
        extends DataDictionary<TMSourceOcrType> {
    private static final long serialVersionUID = 1L;
    public static final String REJECTED = "1";
    public static final String QUOTATION = "2";

    public TMSourceOcrType() {
    }

    public TMSourceOcrType(String typeid) {
        setId(typeid);
    }

    public TMSourceOcrType(Item item) {
        load(item);
    }


    public boolean isRejected() {
        return isType("1");
    }

    public boolean isQuotation() {
        return isType("2");
    }
}
