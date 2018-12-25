package com.kasuo.crawler.domain.crm;

public class SourceInputStat {
    protected int totalTmNums = 0;
    protected int validTmNums = 0;
    protected int validOrgNums = 0;
    protected int newOrgNums = 0;
    protected int newTmNums = 0;

    public int getTotalTmNums() {
        return this.totalTmNums;
    }

    public void setTotalTmNums(int totalTmNums) {
        this.totalTmNums = totalTmNums;
    }

    public int getValidTmNums() {
        return this.validTmNums;
    }

    public void setValidTmNums(int validTmNums) {
        this.validTmNums = validTmNums;
    }

    public int getValidOrgNums() {
        return this.validOrgNums;
    }

    public void setValidOrgNums(int validOrgNums) {
        this.validOrgNums = validOrgNums;
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
}
