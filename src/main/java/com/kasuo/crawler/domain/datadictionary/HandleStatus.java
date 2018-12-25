package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class HandleStatus
        extends DataDictionary<HandleStatus> {
    private static final long serialVersionUID = 1L;
    public static final String NOHANDLE = "0";
    public static final String HANDLED = "2";

    public HandleStatus() {
    }

    public HandleStatus(String typeid) {
        setId(typeid);
    }

    public HandleStatus(Item item) {
        load(item);
    }


    public boolean isHandled() {
        return isType("2");
    }
}
