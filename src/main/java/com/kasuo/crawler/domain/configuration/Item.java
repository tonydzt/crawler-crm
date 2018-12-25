package com.kasuo.crawler.domain.configuration;

import java.io.Serializable;

public class Item
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  
  public Item() {}
  
  private String key = null;
  
  private String value = null;
  
  private String label = null;
  
  private String enLabel = null;
  
  public String getKey() {
    return key;
  }
  
  public void setKey(String key) {
    this.key = key;
  }
  
  public String getValue() {
    return value;
  }
  
  public void setValue(String value) {
    this.value = value;
  }
  
  public String getLabel() {
    return label;
  }
  
  public void setLabel(String label) {
    this.label = label;
  }
  
  public String getEnLabel() {
    return enLabel;
  }
  
  public void setEnLabel(String enLabel) {
    this.enLabel = enLabel;
  }
}
