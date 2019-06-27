package com.servletProject.librarySystem.controller.generalActions;

import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.OnlineOrderModel;
import com.servletProject.librarySystem.service.LibrarianService;
import com.servletProject.librarySystem.utils.GeneralActionsHelper;
import com.servletProject.librarySystem.utils.QueryResponseUtility;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/orders/complete")
public class CompletedOrdersController extends HttpServlet {
    private final LibrarianService librarianService = new LibrarianService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request != null) {
            final HttpSession session = request.getSession();
            try {
                String readerEmail = request.getParameter("reader_email");
                getCompletedOrdersListByUser(request, response, session, readerEmail, "/all-orders");
            } catch (SQLException e) {
                response.setStatus(500);
            } catch (NullPointerException e) {
                QueryResponseUtility.redirectOnAuthorization(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request != null) {
            final HttpSession session = request.getSession();
            try {
                UserEntity user = (UserEntity) session.getAttribute("user");
                if (user != null) {
                    final String mail = user.getMail();
                    getCompletedOrdersListByUser(request, response, session, mail, "/my-library");
                } else {
                    response.setStatus(400);
                }
            } catch (SQLException e) {
                response.setStatus(500);
            } catch (NullPointerException e) {
                QueryResponseUtility.redirectOnAuthorization(request, response);
            }
        }
    }

    private void getCompletedOrdersListByUser(HttpServletRequest request, HttpServletResponse response,
                                              HttpSession session, String readerEmail, String path)
            throws SQLException, ServletException, IOException {
        List<OnlineOrderModel> listOfCompletedOrders = librarianService.getListOfBooksHeldByReader(readerEmail);
        GeneralActionsHelper.giveAnswerToCompletedOrders(listOfCompletedOrders, session, request, response, path);
    }
}