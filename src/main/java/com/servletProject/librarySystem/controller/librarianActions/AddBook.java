package com.servletProject.librarySystem.controller.librarianActions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddBook extends HttpServlet {

    private Map<String, String> paramMap = new HashMap<>();
    private List<String> paramList = Arrays.asList("bookTitle", "bookAuthor", "publication", "genre", "totalAmount");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
