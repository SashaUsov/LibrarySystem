package com.servletProject.librarySystem.controller.booksActions;

import com.servletProject.librarySystem.service.BooksService;
import com.servletProject.librarySystem.utils.FilterUtil;
import com.servletProject.librarySystem.utils.WorkWithHttpRequestUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/books/catalog")
public class BookCatalogAction extends HttpServlet {
    private final BooksService booksService = new BooksService();
    private Map<String, String> paramMap = new HashMap<>();
    private List<String> paramList = Arrays.asList("book_title", "book_author", "publication", "genre");

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request != null) {
            saveBook(request, response);
        } else {
            response.setStatus(422);
            @SuppressWarnings("unchecked") HttpSession session = request.getSession();
            FilterUtil.sendMessage(request, response, session, "Something went wrong :(");
        }
    }

    private void saveBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WorkWithHttpRequestUtil.getAllParam(request, paramList, paramMap);
        try {
            booksService.saveBook(paramMap);
            response.setStatus(201);
            HttpSession session = request.getSession();
            FilterUtil.sendMessage(request, response, session, "Book successfully added to catalog!");
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(500);
        }
    }
}
