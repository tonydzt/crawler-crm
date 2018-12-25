package com.kasuo.crawler.domain.datadictionary;

import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;
import com.kasuo.crawler.domain.configuration.Item;
import com.kasuo.crawler.domain.core.DataDictionary;

public class ContactType extends DataDictionary<ContactType>
{
  private static final long serialVersionUID = 1L;
  public static final String TEL = "1";
  public static final String QQ = "2";
  public static final String WX = "3";
  public static final String MAIL = "4";
  public static final String SMS = "5";
  public static final String OTHER = "0";
  
  public ContactType() {}
  
  public ContactType(String typeid)
  {
    setId(typeid);
  }
  
  public ContactType(Item item) {
    load(item);
  }
  
























  public boolean isTel()
  {
    return isType("1");
  }
  
  public boolean isQQ() {
    return isType("2");
  }
  
  public boolean isWX() {
    return isType("3");
  }
  
  public boolean isSMS() {
    return isType("5");
  }
  
  public boolean isMAIL() {
    return isType("4");
  }
  
  public boolean isOTHER() {
    return isType("0");
  }
}
