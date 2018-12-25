package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class ContactRole extends DataDictionary<ContactRole>
{
  private static final long serialVersionUID = 1L;
  public static final String NOHEAD = "0";
  public static final String HEAD = "1";
  
  public ContactRole() {}
  
  public ContactRole(String typeid) {
    setId(typeid);
  }
  
  public ContactRole(Item item) {
    load(item);
  }
  
  public boolean isHead()
  {
    return isType("1");
  }
}
