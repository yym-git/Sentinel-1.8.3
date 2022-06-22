package com.alibaba.csp.sentinel.dashboard.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ym.y
 * @description
 * @date 15:36 2022/6/22
 */
public class JSONUtils {

    /**
     * @description 将对象转成JSON字符串
     * @author ym.y
     * @date  15:38 2022/6/22
     */
    public static  String toJSONString(Object object) {
        try {
           return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return new ObjectMapper()
                .getTypeFactory()
                .constructParametricType(collectionClass, elementClasses);
    }

    public static <T> List<T> parseObject(Class<T> clazz, String string) {
        JavaType javaType = getCollectionType(ArrayList.class, clazz);
        try {
            return (List<T>) new ObjectMapper().readValue(string, javaType);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
