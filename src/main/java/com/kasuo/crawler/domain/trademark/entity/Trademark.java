package com.kasuo.crawler.domain.trademark.entity;

import com.kasuo.crawler.domain.core.GenericEntity;
import com.kasuo.crawler.domain.datadictionary.CrawlFlag;
import com.kasuo.crawler.domain.datadictionary.TMSourceType;
import com.kasuo.crawler.domain.datadictionary.TrademarkStatus;
import com.kasuo.crawler.domain.org.Org;
import com.kasuo.crawler.domain.permission.User;
import sun.management.Agent;

import java.util.Date;
import java.util.List;


public class Trademark
  extends GenericEntity {
  private static final long serialVersionUID = 1L;
  private Org org = null;
  private String regNum = null;
  private int cls = 0;
  private String spell = null;
  private String nameEng = null;
  private String header = null;
  private String number = null;
  private Date applyDate = null;
  private Date reviewDate = null;
  private Date approvedDate = null;
  private Date expiryDate = null;
  private TrademarkStatus status = null;
  private Agent agent = null;
  private String memo = null;
  private String orgName = null;
  private String agentName = null;
  private String tmAddress = null;
  private User createUser = null;
  private User lastModifyUser = null;
  
  private String crawler = null;
  private Date crawlUpdateDate = null;
  private Date crawlTime = null;
  private CrawlFlag crawlFlag = null;
  private List<TrademarkProcess> flows = null;
  private TMSourceType sourceType = null;
  private Date applyAcceptDate = null;
  private Date rejectedDate = null;
  

  public Trademark() {}
  
  public Trademark(String id)
  {
    this.id = id;
  }
  




  public String getNameShow()
  {
    String s = "";
    if (name != null) {
      if (name.length() > 6) {
        s = name.substring(0, 6);
        return s + "...";
      }
      s = name;
    }
    
    if (nameEng != null) {
      int retain = 15 - 2 * s.length();
      if (retain > 0) {
        if (retain >= nameEng.length()) {
          s = s + nameEng;
        } else {
          s = s + nameEng.substring(0, retain - 3) + "...";
        }
      }
    }
    if (s.equals("")) {
      s = "[图形商标]";
    }
    return s.trim();
  }
  
  public String getNameShowAll() {
    String s = "";
    if (name != null) {
      s = name + " ";
    }
    if (nameEng != null) {
      s = s + nameEng;
    }
    
    if (s.equals("")) {
      s = "[图形商标]";
    }
    return s.trim();
  }
  
  @Override
  public String toString() {
    return "id=" + id + ",regNum=" + regNum + ",cls=" + cls + ",name=" + name;
  }
  
  public Org getOrg() {
    return org;
  }
  
  public void setOrg(Org org) {
    this.org = org;
  }
  
  public String getRegNum() {
    return regNum;
  }
  
  public void setRegNum(String regNum) {
    this.regNum = regNum;
  }
  
  public String getOrgName() {
    return orgName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public String getAgentName() {
    return agentName;
  }
  
  public void setAgentName(String agentName) {
    this.agentName = agentName;
  }
  
  public String getTmAddress() {
    return tmAddress;
  }
  
  public void setTmAddress(String tmAddress) {
    this.tmAddress = tmAddress;
  }
  
  public int getCls() {
    return cls;
  }
  
  public void setCls(int cls) {
    this.cls = cls;
  }
  
  public String getSpell() {
    return spell;
  }
  
  public void setSpell(String spell) {
    this.spell = spell;
  }
  
  public String getNameEng() {
    return nameEng;
  }
  
  public void setNameEng(String nameEng) {
    this.nameEng = nameEng;
  }
  
  public String getHeader() {
    return header;
  }
  
  public void setHeader(String header) {
    this.header = header;
  }
  
  public String getNumber() {
    return number;
  }
  
  public void setNumber(String number) {
    this.number = number;
  }
  
  public Date getApplyDate() {
    return applyDate;
  }
  
  public void setApplyDate(Date applyDate) {
    this.applyDate = applyDate;
  }
  
  public Agent getAgent() {
    return agent;
  }
  
  public void setAgent(Agent agent) {
    this.agent = agent;
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
  
  public Date getReviewDate() {
    return reviewDate;
  }
  
  public void setReviewDate(Date reviewDate) {
    this.reviewDate = reviewDate;
  }
  
  public Date getApprovedDate() {
    return approvedDate;
  }
  
  public void setApprovedDate(Date approvedDate) {
    this.approvedDate = approvedDate;
  }
  
  public Date getExpiryDate() {
    return expiryDate;
  }
  
  public void setExpiryDate(Date expiryDate) {
    this.expiryDate = expiryDate;
  }
  
  public TrademarkStatus getStatus() {
    return status;
  }
  
  public void setStatus(TrademarkStatus status) {
    this.status = status;
  }
  
  public Date getCrawlUpdateDate() {
    return crawlUpdateDate;
  }
  
  public void setCrawlUpdateDate(Date crawlUpdateDate) {
    this.crawlUpdateDate = crawlUpdateDate;
  }
  
  public Date getCrawlTime() {
    return crawlTime;
  }
  
  public void setCrawlTime(Date crawlTime) {
    this.crawlTime = crawlTime;
  }
  
  public CrawlFlag getCrawlFlag() {
    return crawlFlag;
  }
  
  public void setCrawlFlag(CrawlFlag crawlFlag) {
    this.crawlFlag = crawlFlag;
  }
  
  public List<TrademarkProcess> getFlows() {
    return flows;
  }
  
  public void setFlows(List<TrademarkProcess> flows) {
    this.flows = flows;
  }
  
  public String getCrawler() {
    return crawler;
  }
  
  public void setCrawler(String crawler) {
    this.crawler = crawler;
  }
  
  public TMSourceType getSourceType() {
    return sourceType;
  }
  
  public void setSourceType(TMSourceType sourceType) {
    this.sourceType = sourceType;
  }
  
  public Date getApplyAcceptDate() {
    return applyAcceptDate;
  }
  
  public void setApplyAcceptDate(Date applyAcceptDate) {
    this.applyAcceptDate = applyAcceptDate;
  }
  
  public Date getRejectedDate() {
    return rejectedDate;
  }
  
  public void setRejectedDate(Date rejectedDate) {
    this.rejectedDate = rejectedDate;
  }
}
