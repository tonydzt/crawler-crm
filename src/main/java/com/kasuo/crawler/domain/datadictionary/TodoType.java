package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class TodoType extends DataDictionary<TodoType> {
    private static final long serialVersionUID = 1L;
    public static final String NOTICE = "1";
    public static final String TASK = "2";
    public static final String BUSI = "3";

    public TodoType() {
    }

    public TodoType(String typeid) {
        setId(typeid);
    }

    public TodoType(Item item) {
        load(item);
    }


    public boolean isNotice() {
        return isType("1");
    }

    public boolean isTask() {
        return isType("2");
    }

    public boolean isBusi() {
        return isType("3");
    }
}
