package com.kasuo.crawler.common;

/**
 * @author dzt
 */
public class Response {

    private final int code;

    private final String message;

    public Response(int code) {
        this.code = code;
        this.message = "";
    }

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}