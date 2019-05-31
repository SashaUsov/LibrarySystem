package com.servletProject.librarySystem.controller.userActions;

import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.service.UserService;
import com.servletProject.librarySystem.utils.WorkWithHttpRequestUtil;
import org.apache.commons.validator.routines.EmailValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/registration/to-register")
public class RegisterUser extends HttpServlet {
    private UserService userService = new UserService();
    private Map<String, String> paramMap = new HashMap<>();
    private List<String> paramList = Arrays.asList("first_name", "last_name", "nick_name",
                                                   "password", "mail", "address");

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        if (request != null) {
            getAllParam(request);
        }
        if (!isValidEmail(paramMap.get("mail"))) {
            response.setStatus(422);
        } else {
            try {
                final UserEntity saveUser = userService.save(paramMap);
                response.setStatus(201);
            } catch (SQLException e) {
                response.setStatus(500);
            }
        }
    }

    private boolean isValidEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    private void getAllParam(HttpServletRequest request) {
        for (String param : paramList) {
            WorkWithHttpRequestUtil.getCurrentParam(request, param, paramMap);
        }
    }


}
