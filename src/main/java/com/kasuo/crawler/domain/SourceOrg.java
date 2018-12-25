package com.kasuo.crawler.domain;

import com.kasuo.crawler.domain.org.Org;


public class SourceOrg
{
  protected long id;
  protected String inputId;
  protected String queryOrgName;
  protected Org org;
  protected String crawlEvent;
  
  public SourceOrg() {}
  
  public long getId()
  {
    return id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getInputId() {
    return inputId;
  }
  
  public void setInputId(String inputId) {
    this.inputId = inputId;
  }
  
  public String getQueryOrgName() {
    return queryOrgName;
  }
  
  public void setQueryOrgName(String queryOrgName) {
    this.queryOrgName = queryOrgName;
  }
  
  public Org getOrg() {
    return org;
  }
  
  public void setOrg(Org org) {
    this.org = org;
  }
  
  public String getCrawlEvent() {
    return crawlEvent;
  }
  
  public void setCrawlEvent(String crawlEvent) {
    this.crawlEvent = crawlEvent;
  }
  
}
