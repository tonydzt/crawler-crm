package com.kasuo.crawler.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String jsonEncode(Object object) {
        String str = null;
        try {
            str = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("", e);
        }
        return str;
    }

    public static <T> T jsonDecode(String json, Class<T> clazz) {
        T t = null;
        try {
            t = mapper.readValue(json, clazz);
        } catch (IOException e) {
            logger.error("", e);
        }
        return t;
    }

    public static HashMap<String, Object> decodeToMap(String json) {
        HashMap<String, Object> map = null;
        try {
            map = mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {
            });
        } catch (IOException e) {
            logger.error("", e);
        }
        return map;
    }

    public static <T> List<T> decodeToList(String json, Class<T> classType) {
        List<T> list = null;
        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, classType);
            list = mapper.readValue(json, javaType);
        } catch (IOException e) {
            logger.error("", e);
        }
        return list;
    }
}
