package com.kasuo.crawler.domain.datadictionary;


import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class PermissionsResourceType extends DataDictionary<PermissionsResourceType> {
    private static final long serialVersionUID = 1L;
    public static final String MENU = "01";
    public static final String LINK = "02";

    public PermissionsResourceType() {
    }

    public PermissionsResourceType(String typeid) {
        setId(typeid);
    }

    public PermissionsResourceType(Item item) {
        load(item);
    }


    public boolean isMenu() {
        return isType("01");
    }

    public boolean isLink() {
        return isType("02");
    }
}
