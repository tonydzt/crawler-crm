package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class HasAttachFlag
        extends DataDictionary<HasAttachFlag> {
    private static final long serialVersionUID = 1L;
    public static final String NOATTACH = "0";
    public static final String ATTACH = "1";

    public HasAttachFlag() {
    }

    public HasAttachFlag(String typeid) {
        setId(typeid);
    }

    public HasAttachFlag(Item item) {
        load(item);
    }


    public boolean hasAttach() {
        return isType("1");
    }
}
