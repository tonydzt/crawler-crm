package com.kasuo.crawler.domain.crm;

import java.util.List;

public class MailInfo
{
  public static final String ENCODEING = "UTF-8";
  private String host;
  private String sender;
  private List<String> receivers = new java.util.ArrayList();
  
  private String name;
  
  private String username;
  
  public MailInfo() {}
  
  public String getHost() { return host; }
  
  private String password;
  
  public void setHost(String host) { this.host = host; }
  
  private String subject;
  private String message;
  public String getSender() { return sender; }
  

  public void setSender(String sender) {
    this.sender = sender;
  }
  
  public List<String> getReceivers() {
    return receivers;
  }
  
  public void setReceivers(List<String> receivers) {
    this.receivers = receivers;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getUsername() {
    return username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getSubject() {
    return subject;
  }
  
  public void setSubject(String subject) {
    this.subject = subject;
  }
  
  public String getMessage() {
    return message;
  }
  
  public void setMessage(String message) {
    this.message = message;
  }
}
