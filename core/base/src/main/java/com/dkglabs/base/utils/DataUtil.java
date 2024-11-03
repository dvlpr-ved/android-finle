package com.dkglabs.base.utils;

import com.dkglabs.base.manager.LogsManager;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataUtil {

    private static ObjectMapper mapper = null;

    private static ObjectMapper getObjectMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper();
        }
        return mapper;
    }

    public static String getStringFromObj(Object obj) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            return null;
        }

    }

    public static <T> T getObjectFromJson(String json, Class<T> classType) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.readValue(json, classType);
        } catch (Exception e) {
            LogsManager.printLog(e.getMessage());
        }
        return null;
    }
}
