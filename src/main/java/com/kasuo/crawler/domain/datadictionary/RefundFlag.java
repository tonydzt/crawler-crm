package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class RefundFlag
        extends DataDictionary<RefundFlag> {
    private static final long serialVersionUID = 1L;
    public static final String NOREFUND = "0";
    public static final String REFUND = "1";

    public RefundFlag() {
    }

    public RefundFlag(String typeid) {
        setId(typeid);
    }

    public RefundFlag(Item item) {
        load(item);
    }


    public boolean isRefund() {
        return isType("1");
    }
}
