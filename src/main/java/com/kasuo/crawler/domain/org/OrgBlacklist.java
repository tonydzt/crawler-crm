package com.kasuo.crawler.domain.org;

import com.kasuo.crawler.domain.core.GenericEntity;
import com.kasuo.crawler.domain.permission.User;


public class OrgBlacklist
        extends GenericEntity {
    private static final long serialVersionUID = 1L;

    public OrgBlacklist() {
    }

    protected String orgName = null;
    protected String memo = null;
    protected User createUser = null;
    protected User lastModifyUser = null;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public User getLastModifyUser() {
        return lastModifyUser;
    }

    public void setLastModifyUser(User lastModifyUser) {
        this.lastModifyUser = lastModifyUser;
    }
}
