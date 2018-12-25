package com.kasuo.crawler.util;

import java.io.File;
import java.io.FilenameFilter;

public class FileNameFilter implements FilenameFilter
{
  private String[] types;
  
  public FileNameFilter(String type)
  {
    type = type.toUpperCase();
    types = type.split(",");
  }
  
  @Override
  public boolean accept(File dir, String name)
  {
    for (String type : types) {
      if (name.toUpperCase().endsWith(type)) {
        return true;
      }
    }
    return false;
  }
  
  public static void main(String[] args) throws Exception
  {
    String s = ".abc|.dc|";
    String[] ss = s.split("\\|");
    System.out.println(ss.length);
    for (String t : ss) {
      System.out.println(t);
    }
  }
}
