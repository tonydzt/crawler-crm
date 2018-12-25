package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class CompetitorStatus
        extends DataDictionary<CompetitorStatus> {
    private static final long serialVersionUID = 1L;
    public static final String NOCONTACT = "0";
    public static final String CONTACTED = "1";

    public CompetitorStatus() {
    }

    public CompetitorStatus(String typeid) {
        setId(typeid);
    }

    public CompetitorStatus(Item item) {
        load(item);
    }


    public boolean isContacted() {
        return isType("1");
    }
}
