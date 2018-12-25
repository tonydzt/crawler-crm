package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;


public class ContactBookType
        extends DataDictionary<ContactBookType> {
    private static final long serialVersionUID = 1L;
    public static final String TEL = "1";
    public static final String QQ = "2";
    public static final String WX = "3";
    public static final String EMAIL = "4";

    public ContactBookType() {
    }

    public ContactBookType(String typeid) {
        setId(typeid);
    }

    public ContactBookType(Item item) {
        load(item);
    }


    public boolean isTel() {
        return isType("1");
    }

    public boolean isQQ() {
        return isType("2");
    }

    public boolean isWX() {
        return isType("3");
    }

    public boolean isEmail() {
        return isType("4");
    }
}
