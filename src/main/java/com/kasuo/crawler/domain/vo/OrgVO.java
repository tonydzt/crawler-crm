package com.kasuo.crawler.domain.vo;

import com.kasuo.crawler.domain.Org;

/**
 * @author douzhitong
 * @date 2018/12/22
 */
public class OrgVO {

    private Org org;
    private Boolean isNew;

    public OrgVO(Org org, boolean isNew) {
        this.org = org;
        this.isNew = isNew;
    }

    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }
}
