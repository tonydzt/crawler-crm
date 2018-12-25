package com.kasuo.crawler.domain.source;

public class SourceInputTest {

  private int id = 0;
  protected String orgName = null;
  protected int testCount = 1;
  protected String memo = null;
  

  public SourceInputTest() {}
  
  public SourceInputTest(int id)
  {
    this.id = id;
  }
  
  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public String getOrgName() {
    return orgName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public int getTestCount() {
    return testCount;
  }
  
  public void setTestCount(int testCount) {
    this.testCount = testCount;
  }
  
  public String getMemo() {
    return memo;
  }
  
  public void setMemo(String memo) {
    this.memo = memo;
  }
}
