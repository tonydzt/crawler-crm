package com.kasuo.crawler.domain.org;

import com.kasuo.crawler.domain.core.GenericEntity;
import com.kasuo.crawler.domain.permission.User;

public class OrgHisName extends GenericEntity {
    private static final long serialVersionUID = 1L;
    protected String orgName = null;
    protected User createUser = null;
    protected String memo = null;
    protected Org org = null;

    public OrgHisName() {
    }

    public OrgHisName(String id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }
}
