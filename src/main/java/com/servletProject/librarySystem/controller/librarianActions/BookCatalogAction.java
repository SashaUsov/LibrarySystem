package com.servletProject.librarySystem.controller.librarianActions;

import com.servletProject.librarySystem.service.LibrarianService;
import com.servletProject.librarySystem.utils.WorkWithHttpRequestUtil;

import javax.servlet.RequestDispatcher;
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

@WebServlet("/book/catalog")
public class BookCatalogAction extends HttpServlet {
    private final LibrarianService librarianService = new LibrarianService();
    private Map<String, String> paramMap = new HashMap<>();
    private List<String> paramList = Arrays.asList("book_title", "book_author", "publication", "genre");

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request != null) {
            saveBook(request, response);
        } else {
            response.setStatus(422);
            giveAnAnswer(request, response, "Something went wrong :(");
        }
    }

    private void saveBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WorkWithHttpRequestUtil.getAllParam(request, paramList, paramMap);
        try {
            librarianService.saveBook(paramMap);
            response.setStatus(201);
            giveAnAnswer(request, response, "Book successfully added to catalog!");
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(500);
        }
    }

    private void giveAnAnswer(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.getSession().setAttribute("message", message);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/messagepage");
        requestDispatcher.forward(request, response);
    }
}
