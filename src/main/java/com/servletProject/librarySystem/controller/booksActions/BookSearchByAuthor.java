package com.servletProject.librarySystem.controller.booksActions;

import com.servletProject.librarySystem.domen.BookCatalog;
import com.servletProject.librarySystem.exception.DataIsNotCorrectException;
import com.servletProject.librarySystem.service.BooksService;
import com.servletProject.librarySystem.utils.FilterUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/book/search-by-author")
public class BookSearchByAuthor extends HttpServlet {
    private final BooksService booksService = new BooksService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request != null) {
            final String bookTitle = request.getParameter("book_author");
            List<BookCatalog> bookByTitle = new ArrayList<>();
            findBookByTitle(request, response, bookTitle);
        }
    }

    private void findBookByTitle(HttpServletRequest request, HttpServletResponse response, String bookAuthor)
            throws ServletException, IOException {
        List<BookCatalog> bookByAuthor;
        if (!"".equals(bookAuthor) && bookAuthor != null) {
            ifBookAuthorPresent(request, response, bookAuthor);
        } else {
            response.setStatus(422);
            final HttpSession session = request.getSession();
            FilterUtil.sendMessage(request, response, session, "Book not found!");

        }
    }

    private void ifBookAuthorPresent(HttpServletRequest request, HttpServletResponse response, String bookAuthor) throws ServletException, IOException {
        List<BookCatalog> bookByAuthor;
        try {
            bookByAuthor = booksService.getAllBookByAuthor(bookAuthor);
            if (bookByAuthor != null && !bookByAuthor.isEmpty()) {
                ifBooksExist(request, response, bookByAuthor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(500);
            throw new DataIsNotCorrectException("Enter author's name!");
        }
    }

    private void ifBooksExist(HttpServletRequest request, HttpServletResponse response, List<BookCatalog> bookByTitle)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("books_list", bookByTitle);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/searchingresults");
        requestDispatcher.forward(request, response);
    }
}
