package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;


public class SysInfo
        extends DataDictionary<SysInfo> {
    private static final long serialVersionUID = 1L;
    public static final String TW_AREACODE_MAYBE_ERROR = "TW001";
    public static final String UW_UNKNOWN = "UW999";

    public SysInfo() {
    }

    public SysInfo(String typeid) {
        setId(typeid);
    }

    public SysInfo(Item item) {
        load(item);
    }


    public boolean isAreacodeMaybeError() {
        return isType("TW001");
    }

    public boolean isUnknownError() {
        return isType("UW999");
    }
}
