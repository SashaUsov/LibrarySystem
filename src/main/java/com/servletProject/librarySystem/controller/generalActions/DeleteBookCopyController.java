package com.servletProject.librarySystem.controller.generalActions;

import com.servletProject.librarySystem.domen.CopiesOfBooks;
import com.servletProject.librarySystem.service.BooksService;
import com.servletProject.librarySystem.service.LibrarianService;
import com.servletProject.librarySystem.utils.GeneralActionsHelper;
import com.servletProject.librarySystem.utils.QueryResponseUtility;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/books/unusable")
public class DeleteBookCopyController extends HttpServlet {
    private final LibrarianService librarianService = new LibrarianService();
    private final BooksService booksService = new BooksService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req != null) {
            try {
                final List<CopiesOfBooks> copiesOfBooksList = librarianService.unusableConditionBooksList();
                GeneralActionsHelper.giveAnswerToUnusableRequest(copiesOfBooksList, req.getSession(), req, resp, "/unusable-list");
            } catch (SQLException e) {
                resp.setStatus(500);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req != null) {
            Long unusableId = Long.valueOf(req.getParameter("unusable_id"));
            if (unusableId != null) {
                try {
                    booksService.deleteUnusableBookCopy(unusableId);
                    resp.setStatus(201);
                    QueryResponseUtility.sendMessage(req, resp, req.getSession(), "Unusable book deleted successfully!");
                } catch (SQLException e) {
                    resp.setStatus(500);
                    QueryResponseUtility.sendMessage(req, resp, req.getSession(),"Deleting failed :(");
                }
            }
        }
    }
}
