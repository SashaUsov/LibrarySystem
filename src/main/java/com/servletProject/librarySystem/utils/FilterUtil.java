package com.servletProject.librarySystem.utils;

import com.servletProject.librarySystem.domen.UserEntity;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.List;

public class FilterUtil {

    public static long getAccessLevel(List<String> roles) {
        long accessLevel = 0;
        for (String role : roles) {
            switch (role) {
                case "USER" : accessLevel++;
                    break;
                case "ADMIN" : accessLevel++;
                    break;
                case "LIBRARIAN" : accessLevel++;
                    break;
            }
        }
        return accessLevel;
    }

    public static void redirectOnAuthorization(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/authorization");
        requestDispatcher.forward(request, response);
    }

    public static boolean hasAnyRole(UserEntity user) {
        return user.getRole() != null && !user.getRole().isEmpty();
    }
}
