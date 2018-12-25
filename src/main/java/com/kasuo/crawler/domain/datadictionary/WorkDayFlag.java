package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class WorkDayFlag
        extends DataDictionary<WorkDayFlag> {
    private static final long serialVersionUID = 1L;
    public static final String NOWORK = "0";
    public static final String WORK = "1";

    public WorkDayFlag() {
    }

    public WorkDayFlag(String typeid) {
        setId(typeid);
    }

    public WorkDayFlag(Item item) {
        load(item);
    }


    public boolean isWork() {
        return isType("1");
    }
}
