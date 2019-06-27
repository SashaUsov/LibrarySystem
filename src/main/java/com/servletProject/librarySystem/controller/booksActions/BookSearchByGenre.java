package com.servletProject.librarySystem.controller.booksActions;

import com.servletProject.librarySystem.domen.BookCatalog;
import com.servletProject.librarySystem.service.BookCatalogService;
import com.servletProject.librarySystem.utils.QueryResponseUtility;

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
    private final BookCatalogService bookCatalogService = new BookCatalogService(bookRepository, copiesOfBooksRepository);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
        final HttpSession session = request.getSession();
        if (!"".equals(bookGenre) && bookGenre != null) {
            ifBookGenrePresent(request, response, session, bookGenre);
        } else {
            response.setStatus(422);
            QueryResponseUtility.sendMessage(request, response, session, "Enter the book genre");
        }
    }

    private void ifBookGenrePresent(HttpServletRequest request, HttpServletResponse response,
                                    HttpSession session, String bookGenre) throws ServletException, IOException {
        List<BookCatalog> booksByGenre;
        try {
            booksByGenre = bookCatalogService.getAllBookByGenre(bookGenre);
            if (booksByGenre != null && !booksByGenre.isEmpty()) {
                ifBooksExist(request, response, booksByGenre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(500);
            QueryResponseUtility.sendMessage(request, response, session, "Book not found");
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
