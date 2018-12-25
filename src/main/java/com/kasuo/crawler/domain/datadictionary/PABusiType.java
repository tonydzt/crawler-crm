package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;


public class PABusiType
        extends DataDictionary<PABusiType> {
    private static final long serialVersionUID = 1L;
    public static final String INVENTION_REGISTER = "0";
    public static final String UTILITY_REGISTER = "1";
    public static final String APPEARANCE_REGISTER = "2";
    public static final String OTHER = "n";

    public PABusiType() {
    }

    public PABusiType(String typeid) {
        setId(typeid);
    }

    public PABusiType(Item item) {
        load(item);
    }


    public boolean isInventionRegister() {
        return isType("0");
    }

    public boolean isUtilityRegister() {
        return isType("1");
    }

    public boolean isAppearanceRegister() {
        return isType("2");
    }

    public boolean isOther() {
        return isType("n");
    }
}
