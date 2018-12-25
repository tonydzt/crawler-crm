package com.kasuo.crawler.domain.source;

import com.kasuo.crawler.domain.core.GenericEntity;
import com.kasuo.crawler.domain.datadictionary.*;
import com.kasuo.crawler.util.DateTool;

import java.util.Date;

public class SourceInputLog extends GenericEntity {
    private static final long serialVersionUID = 1L;
    protected String batchNo = null;
    protected String fileName = null;
    protected SourceBatchStatus status = new SourceBatchStatus("0");
    protected int totalNums = 0;
    protected int usableNums = 0;
    protected int assignedNums = 0;
    protected int unassignedNums = 0;
    protected int current = 0;
    protected SourceBatchPriority priority = new SourceBatchPriority("1");
    protected SourceBatchNoticeFlag noticeFlag = new SourceBatchNoticeFlag("1");
    protected String memo = null;
    protected SourceBatchSmsFlag smsFlag = new SourceBatchSmsFlag("0");
    protected Date crawlTime = null;
    protected SourceBatchCrawlStatus crawlStatus = new SourceBatchCrawlStatus("1");
    protected int crawlPlanNums = 0;
    protected int crawlPhoneNums = 0;
    protected int crawlTelNums = 0;
    protected int crawlNoneNums = 0;
    protected Date minApplyDate = null;
    protected Date maxApplyDate = null;
    protected int recrawlFlag = 0;


    public int getCrawlRealityNums() {
        return crawlPhoneNums + crawlTelNums;
    }


    public int getCrawlTotalNums() {
        return crawlPhoneNums + crawlTelNums + crawlNoneNums;
    }

    @Override
    public String toString() {
        return "id=" + id + ",fileName=" + fileName + "createTime=" + createTime;
    }


    public String getApplyDateShow() {
        String minDate = "";
        if (minApplyDate != null) {
            minDate = DateTool.getStringDate(minApplyDate);
        }
        String maxDate = "";
        if (maxApplyDate != null) {
            maxDate = DateTool.getStringDate(maxApplyDate);
        }
        return minDate + " ~ " + maxDate;
    }


    public SourceInputLog() {
    }

    public SourceInputLog(String id) {
        this.id = id;
    }

    public SourceBatchNoticeFlag getNoticeFlag() {
        return noticeFlag;
    }

    public void setNoticeFlag(SourceBatchNoticeFlag noticeFlag) {
        this.noticeFlag = noticeFlag;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public SourceBatchStatus getStatus() {
        return status;
    }

    public void setStatus(SourceBatchStatus status) {
        this.status = status;
    }

    public int getTotalNums() {
        return totalNums;
    }

    public void setTotalNums(int totalNums) {
        this.totalNums = totalNums;
    }

    public int getUsableNums() {
        return usableNums;
    }

    public void setUsableNums(int usableNums) {
        this.usableNums = usableNums;
    }

    public int getAssignedNums() {
        return assignedNums;
    }

    public void setAssignedNums(int assignedNums) {
        this.assignedNums = assignedNums;
    }

    public int getUnassignedNums() {
        return unassignedNums;
    }

    public void setUnassignedNums(int unassignedNums) {
        this.unassignedNums = unassignedNums;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public SourceBatchPriority getPriority() {
        return priority;
    }

    public void setPriority(SourceBatchPriority priority) {
        this.priority = priority;
    }

    public SourceBatchSmsFlag getSmsFlag() {
        return smsFlag;
    }

    public void setSmsFlag(SourceBatchSmsFlag smsFlag) {
        this.smsFlag = smsFlag;
    }

    public Date getCrawlTime() {
        return crawlTime;
    }

    public void setCrawlTime(Date crawlTime) {
        this.crawlTime = crawlTime;
    }

    public SourceBatchCrawlStatus getCrawlStatus() {
        return crawlStatus;
    }

    public void setCrawlStatus(SourceBatchCrawlStatus crawlStatus) {
        this.crawlStatus = crawlStatus;
    }

    public int getCrawlPlanNums() {
        return crawlPlanNums;
    }

    public void setCrawlPlanNums(int crawlPlanNums) {
        this.crawlPlanNums = crawlPlanNums;
    }

    public int getCrawlPhoneNums() {
        return crawlPhoneNums;
    }

    public void setCrawlPhoneNums(int crawlPhoneNums) {
        this.crawlPhoneNums = crawlPhoneNums;
    }

    public int getCrawlTelNums() {
        return crawlTelNums;
    }

    public void setCrawlTelNums(int crawlTelNums) {
        this.crawlTelNums = crawlTelNums;
    }

    public int getCrawlNoneNums() {
        return crawlNoneNums;
    }

    public void setCrawlNoneNums(int crawlNoneNums) {
        this.crawlNoneNums = crawlNoneNums;
    }

    public Date getMinApplyDate() {
        return minApplyDate;
    }

    public void setMinApplyDate(Date minApplyDate) {
        this.minApplyDate = minApplyDate;
    }

    public Date getMaxApplyDate() {
        return maxApplyDate;
    }

    public void setMaxApplyDate(Date maxApplyDate) {
        this.maxApplyDate = maxApplyDate;
    }

    public int getRecrawlFlag() {
        return recrawlFlag;
    }

    public void setRecrawlFlag(int recrawlFlag) {
        this.recrawlFlag = recrawlFlag;
    }
}
