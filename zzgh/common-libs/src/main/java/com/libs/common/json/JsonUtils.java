package com.libs.common.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XLKAI on 2017/5/31.
 */
public class JsonUtils {

    private static final ObjectMapper parseMapper;
    private static final ObjectMapper jsonMapper;

    static {
        parseMapper = new ObjectMapper();
        parseMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        parseMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        jsonMapper = new ObjectMapper();
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static ObjectMapper getJson() {
        return jsonMapper;
    }

    public static ObjectMapper getParseJson(){
        return parseMapper;
    }

    public static String stringfy(Object object) {
        try {
            return jsonMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T parseBean(String json, Class<T> tClass) {
        try {
            return parseMapper.readValue(json, tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> parseList(String json, Class<T> tClass) {
        JavaType javaType = getCollectionType(List.class, tClass);
        try {
            return parseMapper.readValue(json, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <K, V> Map<K, V> parseMap(String json, Class<K> kClass, Class<V> vClass) {
        JavaType javaType = getCollectionType(HashMap.class, kClass, vClass);
        try {
            return parseMapper.readValue(json, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object parse(String json, Class objClass, Class... tClass) {
        JavaType javaType = getCollectionType(objClass, tClass);
        try {
            return parseMapper.readValue(json, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasss) {
        return parseMapper.getTypeFactory().constructParametricType(collectionClass, elementClasss);
    }
}
