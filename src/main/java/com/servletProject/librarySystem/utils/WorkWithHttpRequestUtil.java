package com.servletProject.librarySystem.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class WorkWithHttpRequestUtil {

    public static void getAllParam(HttpServletRequest request, List<String> paramList, Map<String, String> paramMap) {
        for (String param : paramList) {
            WorkWithHttpRequestUtil.getCurrentParam(request, param, paramMap);
        }
    }

    private static Map<String, String> getCurrentParam(HttpServletRequest request, String param, Map<String, String> paramMap) {

        final String value = request.getParameter(param);
        if (isExist(value)) {
            paramMap.put(param, value.trim());
        } else {
            throw new IllegalArgumentException();
        }
        return paramMap;
    }

    private static boolean isExist(String param) {
        return param != null && !"".equalsIgnoreCase(param.trim());
    }
}
