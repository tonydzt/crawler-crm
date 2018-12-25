package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class SourceHandleStatus extends DataDictionary<SourceHandleStatus> {
    private static final long serialVersionUID = 1L;
    public static final String NODO = "0";
    public static final String DOING = "1";
    public static final String DONE = "2";

    public SourceHandleStatus() {
    }

    public SourceHandleStatus(String typeid) {
        setId(typeid);
    }

    public SourceHandleStatus(Item item) {
        load(item);
    }


    public boolean isNoDo() {
        return isType("0");
    }

    public boolean isDoing() {
        return isType("1");
    }

    public boolean isDone() {
        return isType("2");
    }
}
