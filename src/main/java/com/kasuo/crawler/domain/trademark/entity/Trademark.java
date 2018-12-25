package com.kasuo.crawler.domain.trademark.entity;

import com.kasuo.crawler.domain.core.GenericEntity;
import com.kasuo.crawler.domain.datadictionary.TrademarkStatus;
import com.kasuo.crawler.domain.org.Org;

public class Trademark
  extends GenericEntity {
  private Org org = null;
  private String regNum = null;
  private int cls = 0;
  private TrademarkStatus status = null;

  private String crawler = null;

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

  public TrademarkStatus getStatus() {
    return status;
  }
  
  public void setStatus(TrademarkStatus status) {
    this.status = status;
  }

  public String getCrawler() {
    return crawler;
  }
  
  public void setCrawler(String crawler) {
    this.crawler = crawler;
  }
  
}
