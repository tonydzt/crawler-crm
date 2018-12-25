package com.kasuo.crawler.common;

import java.util.HashMap;
import java.util.Map;


/**
 * @author dzt
 */
public class ResponseData extends Response {

    private Object data;

    public ResponseData() {
        super(0, "SUCCESS");
    }

    public ResponseData(int code) {
        super(code);
    }

    public ResponseData(int code, String message) {
        super(code, message);
    }

    public ResponseData(int code, String message, Object data) {
        super(code, message);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Map<String,Object> dataMap() {

        if (data == null) {
            this.data = new HashMap<>();
            return (Map<String,Object>)data;
        }

        if (data instanceof Map) {
            return (Map<String,Object>)data;
        }

        return null;
    }

}
