package com.servletProject.librarySystem.controller.booksActions;

import com.servletProject.librarySystem.service.BooksService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/book/search-by-genre")
public class BookSearchByGenre extends HttpServlet {
    private final BooksService booksService = new BooksService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
