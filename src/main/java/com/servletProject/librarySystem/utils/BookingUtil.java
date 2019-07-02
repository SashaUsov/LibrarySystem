package com.servletProject.librarySystem.utils;

import com.servletProject.librarySystem.domen.dto.onlineOrderBook.OnlineOrderModel;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class BookingUtil {

    public static void getReserveListAnswer(List<OnlineOrderModel> listOfReservedBooks, ServletRequest request,
                                            ServletResponse response, HttpSession session, String path)
            throws ServletException, IOException {
        if (listOfReservedBooks != null && !listOfReservedBooks.isEmpty()) {
            session.setAttribute("list_of_reserved_books", listOfReservedBooks);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
            requestDispatcher.forward(request, response);
        } else {
            QueryResponseUtility.sendMessage(request, response, session, "There are no pending orders.");
        }
    }
}