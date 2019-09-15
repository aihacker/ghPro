package com.libs.common.xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XLKAI on 2017/7/8.
 */
public class XmlUtils {

    private static XmlMapper strinfy;
    private static XmlMapper parse;

    static {
        strinfy = new XmlMapper();

        parse = new XmlMapper();
        parse.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static XmlMapper getParse() {
        return parse;
    }

    public static String strinfy(Object object) {
        try {
            return strinfy.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T parseBean(String xml, Class<T> tClass) {
        try {
            return parse.readValue(xml, tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> parseList(String xml, Class<T> tClass) {
        try {
            return parse.readValue(xml, getJavaType(ArrayList.class, List.class, tClass));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JavaType getJavaType(Class<?> listClass, Class<?> listpClass, Class<?>... elementClass) {
        return parse.getTypeFactory().constructParametrizedType(listClass, listpClass, elementClass);
    }

}
