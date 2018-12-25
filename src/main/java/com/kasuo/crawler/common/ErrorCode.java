package com.kasuo.crawler.common;

/**
 * @author dzt
 */
public class ErrorCode {

    public static final Response OK_EMPTY = new Response(0, "SUCCESS");

    public static final Response ERROR_NO_PATH = new Response(1, "NO PATH");
    public static final Response ERROR_NOT_PATH = new Response(2, "NOT PATH");
    public static final Response ERROR_EMPTY_PATH = new Response(3, "EMPTY PATH");

}
