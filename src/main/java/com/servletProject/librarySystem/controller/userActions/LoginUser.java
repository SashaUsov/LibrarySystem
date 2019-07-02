package com.servletProject.librarySystem.controller.userActions;

import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.service.UserControllerService;
import com.servletProject.librarySystem.utils.WorkWithHttpRequestUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/login/enter")
public class LoginUser extends HttpServlet {
//    private UserControllerService userControllerService = new UserControllerService(userRepository, userRoleRepository, userService);
//    private Map<String, String> paramMap = new HashMap<>();
//    private List<String> paramList = Arrays.asList("nick_name", "password");
//
//    @Override
//    public void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws IOException, ServletException {
//        if (request != null) {
//            WorkWithHttpRequestUtil.getAllParam(request, paramList, paramMap);
//            try {
//                UserEntity user = userControllerService.login(paramMap);
//                if (user != null) {
//                    ifLoginSuccessful(request, response, user);
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        }
//
//    private void ifLoginSuccessful(HttpServletRequest request, HttpServletResponse response, UserEntity user) throws ServletException, IOException {
//        user.setLogin(true);
//        user.setPassword(null);
//        HttpSession session = request.getSession();
//        session.setAttribute("user", user);
//        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/");
//        requestDispatcher.forward(request, response);
//    }
}
