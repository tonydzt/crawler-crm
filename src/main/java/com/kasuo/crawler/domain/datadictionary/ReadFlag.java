package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class ReadFlag
        extends DataDictionary<ReadFlag> {
    private static final long serialVersionUID = 1L;
    public static final String NO_READ = "0";
    public static final String READED = "1";

    public ReadFlag() {
    }

    public ReadFlag(String typeid) {
        setId(typeid);
    }

    public ReadFlag(Item item) {
        load(item);
    }


    public boolean isReaded() {
        return isType("1");
    }
}
