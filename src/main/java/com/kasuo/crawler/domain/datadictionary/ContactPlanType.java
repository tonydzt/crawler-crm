package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class ContactPlanType
        extends DataDictionary<ContactPlanType> {
    private static final long serialVersionUID = 1L;
    public static final String NOTICE = "1";
    public static final String REVISIT = "2";

    public ContactPlanType() {
    }

    public ContactPlanType(String typeid) {
        setId(typeid);
    }

    public ContactPlanType(Item item) {
        load(item);
    }


    public boolean isNotice() {
        return isType("1");
    }


    public boolean isReVisit() {
        return isType("2");
    }
}
