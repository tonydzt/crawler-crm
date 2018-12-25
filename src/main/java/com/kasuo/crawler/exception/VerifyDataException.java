package com.kasuo.crawler.exception;

public class VerifyDataException extends RuntimeException
{
  private static final long serialVersionUID = 1L;
  
  public VerifyDataException() {}
  
  public VerifyDataException(String message)
  {
    super(message);
  }
  
  public VerifyDataException(Throwable cause) {
    super(cause);
  }
  
  public VerifyDataException(String message, Throwable cause) {
    super(message, cause);
  }
}
