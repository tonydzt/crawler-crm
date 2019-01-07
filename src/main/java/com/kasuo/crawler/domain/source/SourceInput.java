package com.kasuo.crawler.domain.source;

import com.kasuo.crawler.domain.core.GenericEntity;
import com.kasuo.crawler.domain.datadictionary.*;
import com.kasuo.crawler.domain.org.Org;
import com.kasuo.crawler.util.DateTool;

/**
 * @author douzhitong
 * @date 18/11/17
 */


public class SourceInput extends GenericEntity {
    protected Org org = null;
    protected String orgName = null;
    protected String tmAddress = null;
    protected ValidFlag validFlag = new ValidFlag("1");
    protected String crawler = null;

    @Override
    public String toString() {
        return "id=" + this.id + ",createTime=" + DateTool.getStringDateTime(this.createTime) + ",orgName=" + this.orgName + ",tmAddress=" + this.tmAddress;
    }

    public SourceInput() {
    }

    public Org getOrg() {
        return this.org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }

    public ValidFlag getValidFlag() {
        return this.validFlag;
    }

    public void setValidFlag(ValidFlag validFlag) {
        this.validFlag = validFlag;
    }


    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getCrawler() {
        return this.crawler;
    }

    public void setCrawler(String crawler) {
        this.crawler = crawler;
    }

}

