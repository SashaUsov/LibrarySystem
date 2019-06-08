package com.servletProject.librarySystem.controller.booksActions;

import com.servletProject.librarySystem.domen.BookCatalog;
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


@WebServlet("/book/search-by-genre")
public class BookSearchByGenre extends HttpServlet {
    private final BooksService booksService = new BooksService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request != null) {
            final String bookTitle = request.getParameter("book_genre");
            List<BookCatalog> bookByTitle = new ArrayList<>();
            findBookByTitle(request, response, bookTitle);
        }
    }

    private void findBookByTitle(HttpServletRequest request, HttpServletResponse response, String bookGenre)
            throws ServletException, IOException {
        List<BookCatalog> booksByGenre;
        if (!"".equals(bookGenre) && bookGenre != null) {
            try {
                booksByGenre = booksService.getAllBookByGenre(bookGenre);
                if (booksByGenre != null && !booksByGenre.isEmpty()) {
                    ifBooksExist(request, response, booksByGenre);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            response.setStatus(500);
            final HttpSession session = request.getSession();
            FilterUtil.sendMessage(request, response, session, "Book not found!");

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
