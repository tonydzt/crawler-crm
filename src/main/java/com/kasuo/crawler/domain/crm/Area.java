package com.kasuo.crawler.domain.crm;

public class Area {
  private static final long serialVersionUID = 1L;
  
  public Area() {}
  
  private int id = 0;
  private int parentId = 0;
  private String name = null;
  private String shortName = null;
  
  private String areaCode = null;
  private String postcode = null;
  
  private int level = 0;
  private int sort = 0;
  private int status = 0;
  
  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public int getParentId() {
    return parentId;
  }
  
  public void setParentId(int parentId) {
    this.parentId = parentId;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getShortName() {
    return shortName;
  }
  
  public void setShortName(String shortName) {
    this.shortName = shortName;
  }
  
  public String getAreaCode() {
    return areaCode;
  }
  
  public void setAreaCode(String areaCode) {
    this.areaCode = areaCode;
  }
  
  public String getPostcode() {
    return postcode;
  }
  
  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }
  
  public int getLevel() {
    return level;
  }
  
  public void setLevel(int level) {
    this.level = level;
  }
  
  public int getSort() {
    return sort;
  }
  
  public void setSort(int sort) {
    this.sort = sort;
  }
  
  public int getStatus() {
    return status;
  }
  
  public void setStatus(int status) {
    this.status = status;
  }
  
  public static long getSerialversionuid() {
    return 1L;
  }
  
  @Override
  public String toString() {
    return "id=" + id + ",name=" + name + ",leve=" + level + ",parentId=" + parentId;
  }
}
