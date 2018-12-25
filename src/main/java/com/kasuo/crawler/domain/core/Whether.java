package com.kasuo.crawler.domain.core;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.configuration.Item;

public class Whether extends DataDictionary<Whether> {
  private static final long serialVersionUID = 1649094966463058900L;
  public static final String YES = "Whether01";
  public static final String NO = "Whether02";
  
  public Whether() {}
  
  public Whether(String typeid)
  {
    setId(typeid);
  }
  
  public Whether(Item item) {
    load(item);
  }
  
  public boolean isYes()
  {
    return isType("Whether01");
  }
  
  public boolean isNo() {
    return isType("Whether02");
  }
}
