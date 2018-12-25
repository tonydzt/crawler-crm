package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;


public class SourceBatchSmsFlag
        extends DataDictionary<SourceBatchSmsFlag> {
    private static final long serialVersionUID = 1L;
    public static final String NO_SEND_SMS = "0";
    public static final String SENDED_SMS = "1";
    public static final String SEND_LATER_SMS = "2";

    public SourceBatchSmsFlag() {
    }

    public SourceBatchSmsFlag(String typeid) {
        setId(typeid);
    }

    public SourceBatchSmsFlag(Item item) {
        load(item);
    }


    public boolean isSendedSMS() {
        return isType("1");
    }

    public boolean isNoSendedSMS() {
        return isType("0");
    }

    public boolean isSendLaterSMS() {
        return isType("2");
    }
}
