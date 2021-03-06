package com.servletProject.librarySystem.controller.generalActions;

import com.servletProject.librarySystem.domen.UserOrdersTransferObject;
import com.servletProject.librarySystem.service.LibrarianService;
import com.servletProject.librarySystem.utils.GeneralActionsHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/all/completed")
public class AllCompletedOrdersList extends HttpServlet {
    private final LibrarianService librarianService = new LibrarianService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        try {
            List<UserOrdersTransferObject> listOfAllCompletedOrders = librarianService.getListOfAllCompletedOrders();
            GeneralActionsHelper.giveAnswerToCompletedOrders(listOfAllCompletedOrders, session, req, resp, "/all-orders");
        } catch (SQLException e) {
            resp.setStatus(500);
        }
    }
}