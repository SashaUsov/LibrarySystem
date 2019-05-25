package com.servletProject.librarySystem.controller.userActions;

import com.servletProject.librarySystem.utils.WorkWithHttpRequestUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class RegisterUser extends HttpServlet {
    private Map<String, String> paramMap = new HashMap<>();
    private List<String> paramList = Arrays.asList("firstName", "lastName", "nickName", "password",
                                                   "birthday", "gender", "mail", "phoneNumber",
                                                   "address", "role");

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        if (request != null) {
            HttpSession hs = request.getSession(true);
            getAllParam(hs);
        }
    }

    private void getAllParam(HttpSession hs) {
        for (String param : paramList) {
            WorkWithHttpRequestUtil.getCurrentParam(hs, param, paramMap);
        }
    }
}
