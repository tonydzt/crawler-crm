package com.kasuo.crawler.exception;

public class DOMParseException extends RuntimeException
{
  private static final long serialVersionUID = 1L;
  
  public DOMParseException() {}
  
  public DOMParseException(String message)
  {
    super(message);
  }
  
  public DOMParseException(Throwable cause) {
    super(cause);
  }
  
  public DOMParseException(String message, Throwable cause) {
    super(message, cause);
  }
}
