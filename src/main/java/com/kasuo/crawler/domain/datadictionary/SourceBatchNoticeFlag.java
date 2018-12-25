package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class SourceBatchNoticeFlag extends DataDictionary<SourceBatchNoticeFlag> {
    private static final long serialVersionUID = 1L;
    public static final String NONOTICE = "0";
    public static final String NOTICE = "1";

    public SourceBatchNoticeFlag() {
    }

    public SourceBatchNoticeFlag(String typeid) {
        setId(typeid);
    }

    public SourceBatchNoticeFlag(Item item) {
        load(item);
    }


    public boolean isNotice() {
        return isType("1");
    }
}
