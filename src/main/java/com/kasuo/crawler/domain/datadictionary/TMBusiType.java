package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class TMBusiType extends DataDictionary<TMBusiType> {
    private static final long serialVersionUID = 1L;
    public static final String REGISTER = "0";
    public static final String REJECTED = "1";
    public static final String OBJECTION_NOMINATED = "2";
    public static final String CANCEL_FOR_NOUSE_NOMINATED = "3";
    public static final String INVALIDATION_NOMINATED = "4";
    public static final String CANCEL_FOR_NO_EXTENSION = "5";
    public static final String REVOKED = "6";
    public static final String INVALIDATION = "7";
    public static final String EXTENSION_NEARBY = "8";
    public static final String EXTENSION_WAIT = "9";
    public static final String EXTENSION_PASS = "10";
    public static final String REVIEW_NEARBY = "20";
    public static final String TRANSFER = "30";
    public static final String CHANGE_REGISTER_INFO = "40";
    public static final String PRIORITY_APPLY = "50";
    public static final String REGISTER_INTERNATION = "60";
    public static final String OTHER = "n";

    public TMBusiType() {
    }

    public TMBusiType(String typeid) {
        setId(typeid);
    }

    public TMBusiType(Item item) {
        load(item);
    }


    public boolean isRegister() {
        return isType("0");
    }


    public boolean isRejected() {
        return isType("1");
    }


    public boolean isObjectionNominated() {
        return isType("2");
    }


    public boolean isCancelForNouseNominated() {
        return isType("3");
    }


    public boolean isInvalidationNominated() {
        return isType("4");
    }


    public boolean isCancelForNoExtension() {
        return isType("5");
    }


    public boolean isRevoked() {
        return isType("6");
    }


    public boolean isInvalidation() {
        return isType("7");
    }


    public boolean isReviewNearby() {
        return isType("20");
    }


    public boolean isExtensionNearby() {
        return isType("8");
    }


    public boolean isExtensionWait() {
        return isType("9");
    }


    public boolean isExtensionPass() {
        return isType("10");
    }


    public boolean isTransfer() {
        return isType("30");
    }


    public boolean isChangeRegisterInfo() {
        return isType("40");
    }


    public boolean isPriorityApply() {
        return isType("50");
    }


    public boolean isRegisterInternation() {
        return isType("60");
    }


    public boolean isOther() {
        return isType("n");
    }
}
