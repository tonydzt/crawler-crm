package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.core.DataDictionary;

public class OrgType extends DataDictionary<OrgType> {
    private static final long serialVersionUID = 1L;
    public static final String ENTERPRISE = "1";
    public static final String INDIVIDUAL = "2";
    public static final String GOVERNMENT = "3";
    public static final String INSTITUTION = "4";
    public static final String SOCIETY = "5";
    public static final String OTHER = "0";

    public OrgType(String typeid) {
        setId(typeid);
    }

    public boolean isEnterprise() {
        return isType("1");
    }

    public boolean isIndividual() {
        return isType("2");
    }

    public boolean isGovernment() {
        return isType("3");
    }

    public boolean isInstitution() {
        return isType("4");
    }

    public boolean isSociety() {
        return isType("5");
    }

    public boolean isOther() {
        return isType("0");
    }
}
