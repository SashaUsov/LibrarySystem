package com.servletProject.librarySystem.controller.generalActions;

import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.service.LibrarianControllerService;
import com.servletProject.librarySystem.utils.QueryResponseUtility;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/complete/order")
public class GiveOrTakeABookActions extends HttpServlet {
    private final LibrarianControllerService librarianControllerService = new LibrarianControllerService(userService, copiesOfBooksService, orderBookService, bookCatalogService, completedOrdersService);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request != null) {
            final HttpSession session = request.getSession();
            try {
                Long userId = Long.valueOf(request.getParameter("reader_id"));
                Long copyId = Long.valueOf(request.getParameter("book_copy_id"));
                UserEntity user = (UserEntity) session.getAttribute("user");
                librarianControllerService.giveBookToTheReader(copyId, userId, user.getId());
                QueryResponseUtility.sendMessage(request, response, session, "Order completed successfully!");
            } catch (SQLException e) {
                response.setStatus(500);
            } catch (NullPointerException e) {
                QueryResponseUtility.redirectOnAuthorization(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long copyId = Long.valueOf(request.getParameter("order_book_copy_id"));
            Long userId = Long.valueOf(request.getParameter("order_user_id"));
            String bookCondition = request.getParameter("book_condition");
            if (bookCondition != null && !bookCondition.isEmpty()) {
                librarianControllerService.returnBookToTheCatalog(userId, copyId, bookCondition);
                response.setStatus(201);
                QueryResponseUtility.sendMessage(request, response,
                                                 request.getSession(), "Book is successfully returned to the catalog");
            } else {
                response.setStatus(400);
            }
        } catch (SQLException e) {
            response.setStatus(500);
        }
    }
}