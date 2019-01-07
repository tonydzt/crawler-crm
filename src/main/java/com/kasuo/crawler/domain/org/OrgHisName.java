package com.kasuo.crawler.domain.org;

import com.kasuo.crawler.domain.core.GenericEntity;

public class OrgHisName extends GenericEntity {
    protected String orgName = null;
    protected Org org = null;

    public OrgHisName() {
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }
}
