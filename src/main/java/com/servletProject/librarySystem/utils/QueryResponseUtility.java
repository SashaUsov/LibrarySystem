package com.servletProject.librarySystem.utils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class QueryResponseUtility {

    public static void redirectOnAuthorization(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/authorization");
        requestDispatcher.forward(request, response);
    }
}