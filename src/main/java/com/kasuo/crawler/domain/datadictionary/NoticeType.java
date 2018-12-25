package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;


public class NoticeType
        extends DataDictionary<NoticeType> {
    private static final long serialVersionUID = 1L;
    public static final String NOTICED = "1";
    public static final String NO_NOTICE = "0";

    public NoticeType() {
    }

    public NoticeType(String typeid) {
        setId(typeid);
    }

    public NoticeType(Item item) {
        load(item);
    }


    public boolean isNoticed() {
        return isType("1");
    }


    public boolean isNoNotice() {
        return isType("0");
    }
}
