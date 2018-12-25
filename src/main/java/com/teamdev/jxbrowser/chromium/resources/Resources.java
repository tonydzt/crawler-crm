package com.teamdev.jxbrowser.chromium.resources;

import javax.swing.ImageIcon;




public class Resources
{
  public Resources() {}
  
  public static ImageIcon getIcon(String fileName)
  {
    return new ImageIcon(Resources.class.getClassLoader().getResource(fileName));
  }

  public static void main(String[] args) {
    System.out.println(Resources.class.getClassLoader().getResource("META-INF/close-pressed.png"));
  }
}
