package com.kasuo.crawler.domain.core;

import java.io.Serializable;

public class DataDictionary<T> implements Serializable {
  private static final long serialVersionUID = 1L;
  protected StringBuilder keybuf = new StringBuilder("datadictionary.");
  protected String id;
  protected String name;

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

  public boolean isType(String key) {
    if (key == null) {
      return false;
    }
    return key.equals(id);
  }
}
