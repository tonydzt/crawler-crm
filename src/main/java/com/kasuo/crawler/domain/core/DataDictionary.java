package com.kasuo.crawler.domain.core;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.configuration.Item;

import java.io.Serializable;

public class DataDictionary<T> implements Serializable {
  private static final long serialVersionUID = 1L;
  protected StringBuilder keybuf = new StringBuilder("datadictionary.");
  protected String id;
  protected String name;
  protected String englishName;
  protected String value;
  
  public DataDictionary() {
    keybuf.append(getClass().getSimpleName()).append(".");
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getId() {
    return id;
  }
  
  public String getName() {
    return name;
  }
  
  public String getEnglishName() {
    return englishName;
  }
  
  public String getValue() {
    return value;
  }
  
  public void setValue(String value) {
    this.value = value;
  }
  
  public void load(Item item) {
    if (item != null) {
      id = item.getKey();
      name = item.getLabel();
      englishName = item.getEnLabel();
      value = item.getValue();
    }
  }
  
  public boolean isType(String key) {
    if (key == null) {
      return false;
    }
    return key.equals(id);
  }
}
