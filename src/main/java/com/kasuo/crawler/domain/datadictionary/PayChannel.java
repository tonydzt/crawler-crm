package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class PayChannel extends DataDictionary<PayChannel> {
    private static final long serialVersionUID = 1L;
    public static final String COR_BANK = "11";
    public static final String COR_ALIPAY = "12";
    public static final String COR_WX = "13";
    public static final String PER_BANK = "21";
    public static final String PER_ALIPAY = "22";
    public static final String PER_WX = "23";

    public PayChannel() {
    }

    public PayChannel(String typeid) {
        setId(typeid);
    }

    public PayChannel(Item item) {
        load(item);
    }


    public boolean isCorBank() {
        return isType("11");
    }

    public boolean isCorAlipay() {
        return isType("12");
    }

    public boolean isCorWX() {
        return isType("13");
    }

    public boolean isPerBank() {
        return isType("21");
    }

    public boolean isPerAlipay() {
        return isType("22");
    }

    public boolean isPerWX() {
        return isType("23");
    }
}
