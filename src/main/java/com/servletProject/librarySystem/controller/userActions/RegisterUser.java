package com.servletProject.librarySystem.controller.userActions;

import com.servletProject.librarySystem.server.LiquibaseRun;
import com.servletProject.librarySystem.utils.WorkWithHttpRequestUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/registration")
public class RegisterUser extends HttpServlet {
    private Map<String, String> paramMap = new HashMap<>();
    private List<String> paramList = Arrays.asList("firstName", "lastName", "nickName",
                                                   "password", "gender", "mail",
                                                   "phoneNumber", "address", "role");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("sdfghj");
    }

    //        @Override
//    public void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws IOException, ServletException {
//
//        if (request != null) {
//            HttpSession hs = request.getSession(true);
//            getAllParam(hs);
//        }
//    }
//
//    private void getAllParam(HttpSession hs) {
//        for (String param : paramList) {
//            WorkWithHttpRequestUtil.getCurrentParam(hs, param, paramMap);
//        }
//    }


}
