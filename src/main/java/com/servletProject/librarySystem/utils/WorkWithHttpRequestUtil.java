package com.servletProject.librarySystem.utils;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class WorkWithHttpRequestUtil {

    public static Map<String, String> getCurrentParam(HttpSession hs, String param, Map<String, String> paramMap) {

        final String value = (String) hs.getAttribute(param);
        if (isExist(value)) {
            paramMap.put(param, value.trim());
        } else {
            throw new IllegalArgumentException();
        }
        return paramMap;
    }

    public static boolean isExist(String param) {
        return param != null && !"".equalsIgnoreCase(param.trim());
    }
}
