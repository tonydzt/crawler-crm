package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class ContactPlanStatus
        extends DataDictionary<ContactPlanStatus> {
    private static final long serialVersionUID = 1L;
    public static final String UNTOUCH = "0";
    public static final String CONTACTED = "1";

    public ContactPlanStatus() {
    }

    public ContactPlanStatus(String typeid) {
        setId(typeid);
    }

    public ContactPlanStatus(Item item) {
        load(item);
    }


    public boolean isUntouch() {
        return isType("0");
    }


    public boolean isContacted() {
        return isType("1");
    }
}
