package com.kasuo.crawler.domain.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Constants implements Serializable {
  private static final long serialVersionUID = 1L;
  
  public Constants() {}
  
  private String id = null;
  
  protected LinkedHashMap<String, Constant> constants = new LinkedHashMap();
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public synchronized void addConstant(Constant constant) {
    constants.put(constant.getId(), constant);
  }
  
  public Constant getConstant(String id) {
    return (Constant)constants.get(id);
  }
  
  public boolean cantain(String id) {
    return constants.containsKey(id);
  }
  
  public ArrayList<String> getConstantIds() {
    return new ArrayList(constants.keySet());
  }
  
  public List<Constant> getConstants() {
    return new ArrayList(constants.values());
  }
  
  protected synchronized void clear() {
    if (constants != null) {
      constants.clear();
    }
  }
}
