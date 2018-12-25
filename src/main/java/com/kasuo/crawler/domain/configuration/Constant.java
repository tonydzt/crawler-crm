package com.kasuo.crawler.domain.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

public class Constant implements Serializable {
  private static final long serialVersionUID = 1L;
  
  public Constant() {}
  
  private String id = null;
  
  protected LinkedHashMap<String, Item> items = new LinkedHashMap();
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public void addItem(Item item) {
    items.put(item.getKey(), item);
  }
  
  public Item getItem(String key) {
    return (Item)items.get(key);
  }
  
  public List<String> getItemKeys() {
    return new ArrayList(items.keySet());
  }
  
  public List<String> getItemValues() {
    Collection<Item> itemlist = items.values();
    ArrayList<String> itemvalues = new ArrayList(itemlist.size());
    for (Item item : itemlist) {
      itemvalues.add(item.getValue());
    }
    return itemvalues;
  }
  
  public List<Item> getItems() {
    return new ArrayList(items.values());
  }
}
