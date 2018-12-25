package com.kasuo.crawler.domain.org;

import com.kasuo.crawler.domain.core.GenericEntity;
import com.kasuo.crawler.domain.datadictionary.SourceBatchPriority;
import com.kasuo.crawler.domain.datadictionary.SourceHandleStatus;
import com.kasuo.crawler.domain.source.SourceInput;


public class OrgCrawl
        extends GenericEntity {
    private static final long serialVersionUID = 1L;
    protected String orgName = null;
    protected SourceBatchPriority crawlPriority = new SourceBatchPriority("1");
    protected SourceHandleStatus status = new SourceHandleStatus("0");
    protected SourceInput input = null;


    public OrgCrawl() {
    }


    public OrgCrawl(String id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public SourceBatchPriority getCrawlPriority() {
        return crawlPriority;
    }

    public void setCrawlPriority(SourceBatchPriority crawlPriority) {
        this.crawlPriority = crawlPriority;
    }

    public SourceHandleStatus getStatus() {
        return status;
    }

    public void setStatus(SourceHandleStatus status) {
        this.status = status;
    }

    public SourceInput getInput() {
        return input;
    }

    public void setInput(SourceInput input) {
        this.input = input;
    }
}
