package com.servletProject.librarySystem.controller.generalActions;

import com.servletProject.librarySystem.domen.dto.onlineOrderBook.OnlineOrderModel;
import com.servletProject.librarySystem.service.BookingControllerService;
import com.servletProject.librarySystem.service.LibrarianControllerService;
import com.servletProject.librarySystem.utils.BookingUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/reserve-data")
public class ReservedBooksListActions extends HttpServlet {
    private final BookingControllerService bookingControllerService = new BookingControllerService(copiesOfBooksService, orderBookService, bookCatalogService, onlineOrderUtil);
    private final LibrarianControllerService librarianControllerService = new LibrarianControllerService(userService, copiesOfBooksService, orderBookService, bookCatalogService, completedOrdersService, usageService);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request != null) {
            final HttpSession session = request.getSession();
            try {
                List<OnlineOrderModel> listOfReservedBooks = bookingControllerService.getListOfAllReservedBooks();
                BookingUtil.getReserveListAnswer(listOfReservedBooks, request, response, session, "/orders");
            } catch (SQLException e) {
                response.setStatus(500);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request != null) {
            final HttpSession session = request.getSession();
            try {
                String userEmail = request.getParameter("user_email");
                List<OnlineOrderModel> listOfReservedBooks = librarianControllerService.getAllReservedBooksByUser(userEmail);
                BookingUtil.getReserveListAnswer(listOfReservedBooks, request, response, session, "/orders");
            } catch (SQLException e) {
                response.setStatus(500);
            }
        }
    }
}