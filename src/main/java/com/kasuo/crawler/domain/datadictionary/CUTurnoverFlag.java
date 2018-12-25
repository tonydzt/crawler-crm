package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class CUTurnoverFlag extends DataDictionary<CUTurnoverFlag> {
    private static final long serialVersionUID = 1L;
    public static final String NO_TURNOVER = "0";
    public static final String TURNOVER = "1";

    public CUTurnoverFlag() {
    }

    public CUTurnoverFlag(String typeid) {
        setId(typeid);
    }

    public CUTurnoverFlag(Item item) {
        load(item);
    }


    public boolean isTurnover() {
        return isType("1");
    }
}
