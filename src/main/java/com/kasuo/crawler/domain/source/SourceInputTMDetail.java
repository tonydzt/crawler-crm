package com.kasuo.crawler.domain.source;

import com.kasuo.crawler.domain.core.GenericEntity;
import com.kasuo.crawler.domain.trademark.entity.Trademark;
import com.kasuo.crawler.util.DateTool;
import com.kasuo.crawler.domain.datadictionary.*;
import com.kasuo.crawler.domain.permission.User;

import java.util.Date;

public class SourceInputTMDetail extends GenericEntity {
  private static final long serialVersionUID = 1L;
  protected SourceInput input = null;
  protected SourceInputLog originLog = null;
  protected Trademark tm = null;
  protected TMBusiType busiType = null;
  protected Date receivedDataDate = null;
  protected Date rejectedDate = null;
  protected Date busiDate = null;
  protected String busiDateShow = null;
  protected Date updateDate = null;
  protected Date noticeDate = null;
  protected Date postDate = null;
  protected Date postArriveDate = null;
  protected Date deadlineDate = null;
  protected String sourceName = null;
  protected String matchedName = null;
  protected TMSourceErrFlag errFlag = new TMSourceErrFlag("0");
  protected String errDesc = null;
  protected String memo = null;
  protected BusiPurpose purpose = new BusiPurpose("2");
  protected String agentName = null;
  protected User createUser = null;
  protected User lastModifyUser = null;
  protected String orgName = null;
  protected String tmAddress = null;
  protected String sourcePic = null;
  protected String quotaPic = null;
  protected String tmPic = null;
  protected String batchNo = null;
  protected SourceBatchSmsFlag smsFlag = new SourceBatchSmsFlag("0");
  protected ReviewFlag reviewFlag = null;
  protected ReviewFlag initReviewFlag = null;
  protected Date initReviewDate = null;
  protected User initReviewUser = null;
  protected ReadFlag readFlag = null;
  
  public SourceInputTMDetail() {}
  
  public String getBusiDateShow() { if (busiDate != null) {
      return DateTool.getStringDate(busiDate);
    }
    return null;
  }
  
  public void setBusiDateShow(String busiDateShow) {
    this.busiDateShow = busiDateShow;
  }
  

  public SourceInput getInput()
  {
    return input;
  }
  
  public String getBatchNo() {
    return batchNo;
  }
  
  public void setBatchNo(String batchNo) {
    this.batchNo = batchNo;
  }
  
  public String getSourcePic() {
    return sourcePic;
  }
  
  public void setSourcePic(String sourcePic) {
    this.sourcePic = sourcePic;
  }
  
  public String getQuotaPic() {
    return quotaPic;
  }
  
  public void setQuotaPic(String quotaPic) {
    this.quotaPic = quotaPic;
  }
  
  public String getTmPic() {
    return tmPic;
  }
  
  public void setTmPic(String tmPic) {
    this.tmPic = tmPic;
  }
  
  public void setInput(SourceInput input) {
    this.input = input;
  }
  
  public String getAgentName() {
    return agentName;
  }
  
  public void setAgentName(String agentName) {
    this.agentName = agentName;
  }
  
  public Trademark getTm() {
    return tm;
  }
  
  public void setTm(Trademark tm) {
    this.tm = tm;
  }
  
  public BusiPurpose getPurpose() {
    return purpose;
  }
  
  public void setPurpose(BusiPurpose purpose) {
    this.purpose = purpose;
  }
  
  public String getMatchedName() {
    return matchedName;
  }
  
  public void setMatchedName(String matchedName) {
    this.matchedName = matchedName;
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
  
  public TMBusiType getBusiType() {
    return busiType;
  }
  
  public void setBusiType(TMBusiType busiType) {
    this.busiType = busiType;
  }
  
  public Date getBusiDate() {
    return busiDate;
  }
  
  public void setBusiDate(Date busiDate) {
    this.busiDate = busiDate;
  }
  
  public Date getNoticeDate() {
    return noticeDate;
  }
  
  public void setNoticeDate(Date noticeDate) {
    this.noticeDate = noticeDate;
  }
  
  public Date getPostArriveDate() {
    return postArriveDate;
  }
  
  public void setPostArriveDate(Date postArriveDate) {
    this.postArriveDate = postArriveDate;
  }
  
  public Date getDeadlineDate() {
    return deadlineDate;
  }
  
  public void setDeadlineDate(Date deadlineDate) {
    this.deadlineDate = deadlineDate;
  }
  
  public String getSourceName() {
    return sourceName;
  }
  
  public void setSourceName(String sourceName) {
    this.sourceName = sourceName;
  }
  
  public TMSourceErrFlag getErrFlag() {
    return errFlag;
  }
  
  public void setErrFlag(TMSourceErrFlag errFlag) {
    this.errFlag = errFlag;
  }
  
  public String getErrDesc() {
    return errDesc;
  }
  
  public void setErrDesc(String errDesc) {
    this.errDesc = errDesc;
  }
  
  public String getMemo() {
    return memo;
  }
  
  public void setMemo(String memo) {
    this.memo = memo;
  }
  
  public String getOrgName() {
    return orgName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public String getTmAddress() {
    return tmAddress;
  }
  
  public void setTmAddress(String tmAddress) {
    this.tmAddress = tmAddress;
  }
  
  public SourceBatchSmsFlag getSmsFlag() {
    return smsFlag;
  }
  
  public void setSmsFlag(SourceBatchSmsFlag smsFlag) {
    this.smsFlag = smsFlag;
  }
  
  public Date getPostDate() {
    return postDate;
  }
  
  public void setPostDate(Date postDate) {
    this.postDate = postDate;
  }
  
  public Date getReceivedDataDate() {
    return receivedDataDate;
  }
  
  public void setReceivedDataDate(Date receivedDataDate) {
    this.receivedDataDate = receivedDataDate;
  }
  
  public Date getUpdateDate() {
    return updateDate;
  }
  
  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }
  
  public SourceInputLog getOriginLog() {
    return originLog;
  }
  
  public void setOriginLog(SourceInputLog originLog) {
    this.originLog = originLog;
  }
  
  public Date getRejectedDate() {
    return rejectedDate;
  }
  
  public void setRejectedDate(Date rejectedDate) {
    this.rejectedDate = rejectedDate;
  }
  
  public ReviewFlag getReviewFlag() {
    return reviewFlag;
  }
  
  public void setReviewFlag(ReviewFlag reviewFlag) {
    this.reviewFlag = reviewFlag;
  }
  
  public ReadFlag getReadFlag() {
    return readFlag;
  }
  
  public void setReadFlag(ReadFlag readFlag) {
    this.readFlag = readFlag;
  }
  
  public ReviewFlag getInitReviewFlag() {
    return initReviewFlag;
  }
  
  public void setInitReviewFlag(ReviewFlag initReviewFlag) {
    this.initReviewFlag = initReviewFlag;
  }
  
  public Date getInitReviewDate() {
    return initReviewDate;
  }
  
  public void setInitReviewDate(Date initReviewDate) {
    this.initReviewDate = initReviewDate;
  }
  
  public User getInitReviewUser() {
    return initReviewUser;
  }
  
  public void setInitReviewUser(User initReviewUser) {
    this.initReviewUser = initReviewUser;
  }
}
