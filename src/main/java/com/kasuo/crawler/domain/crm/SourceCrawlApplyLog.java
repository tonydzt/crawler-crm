package com.kasuo.crawler.domain.crm;

import com.kasuo.crawler.domain.core.GenericEntity;
import com.kasuo.crawler.domain.datadictionary.SourceCrawlBTErrFlag;
import com.kasuo.crawler.domain.datadictionary.SourceCrawlBTStartType;
import com.kasuo.crawler.domain.datadictionary.SourceCrawlBTStatus;

import java.util.Date;

public class SourceCrawlApplyLog extends GenericEntity {
    private static final long serialVersionUID = 1L;
    protected SourceCrawlBTStatus status = new SourceCrawlBTStatus("0");
    protected SourceCrawlBTErrFlag errFlag = new SourceCrawlBTErrFlag("0");
    protected SourceCrawlBTStartType startType = new SourceCrawlBTStartType("0");
    protected int totalNums = 0;
    protected int validNums = 0;
    protected int totalOrgNums = 0;
    protected int validOrgNums = 0;
    protected int newOrgNums = 0;
    protected int newTmNums = 0;
    protected Date minApplyDate = null;
    protected Date maxApplyDate = null;
    protected String memo = null;
    protected String times = null;

    public SourceCrawlApplyLog() {
    }

    public SourceCrawlApplyLog(String id) {
        this.id = id;
    }

    public String getTimes() {
        if ((this.lastModifyTime != null) && (this.createTime != null)) {
            return (this.lastModifyTime.getTime() - this.createTime.getTime()) / 1000L + "���";
        }
        return "-";
    }

    public SourceCrawlBTStatus getStatus() {
        return this.status;
    }

    public void setStatus(SourceCrawlBTStatus status) {
        this.status = status;
    }

    public SourceCrawlBTErrFlag getErrFlag() {
        return this.errFlag;
    }

    public void setErrFlag(SourceCrawlBTErrFlag errFlag) {
        this.errFlag = errFlag;
    }

    public SourceCrawlBTStartType getStartType() {
        return this.startType;
    }

    public void setStartType(SourceCrawlBTStartType startType) {
        this.startType = startType;
    }

    public int getTotalNums() {
        return this.totalNums;
    }

    public void setTotalNums(int totalNums) {
        this.totalNums = totalNums;
    }

    public int getValidNums() {
        return this.validNums;
    }

    public void setValidNums(int validNums) {
        this.validNums = validNums;
    }

    public Date getMinApplyDate() {
        return this.minApplyDate;
    }

    public void setMinApplyDate(Date minApplyDate) {
        this.minApplyDate = minApplyDate;
    }

    public Date getMaxApplyDate() {
        return this.maxApplyDate;
    }

    public void setMaxApplyDate(Date maxApplyDate) {
        this.maxApplyDate = maxApplyDate;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getNewOrgNums() {
        return this.newOrgNums;
    }

    public void setNewOrgNums(int newOrgNums) {
        this.newOrgNums = newOrgNums;
    }

    public int getNewTmNums() {
        return this.newTmNums;
    }

    public void setNewTmNums(int newTmNums) {
        this.newTmNums = newTmNums;
    }

    public int getValidOrgNums() {
        return this.validOrgNums;
    }

    public void setValidOrgNums(int validOrgNums) {
        this.validOrgNums = validOrgNums;
    }

    public int getTotalOrgNums() {
        return this.totalOrgNums;
    }

    public void setTotalOrgNums(int totalOrgNums) {
        this.totalOrgNums = totalOrgNums;
    }
}
