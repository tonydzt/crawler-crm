package com.kasuo.crawler.util;

import java.io.File;

public class FileAscComprator implements java.util.Comparator {
  public FileAscComprator() {}
  
  @Override
  public int compare(Object arg0, Object arg1) { File file1 = (File)arg0;
    File file2 = (File)arg1;
    int fileName1 = Integer.parseInt(getFileNamePrefix(file1.getName()));
    int fileName2 = Integer.parseInt(getFileNamePrefix(file2.getName()));
    
    int ret = fileName1 - fileName2;
    
    if (ret > 0) {
      return 1;
    }
    if (ret < 0) {
      return -1;
    }
    return 0;
  }
  
  private String getFileNamePrefix(String s) {
    return s.substring(0, s.indexOf('.'));
  }
}
