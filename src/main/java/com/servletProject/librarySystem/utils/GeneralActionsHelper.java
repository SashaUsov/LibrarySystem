package com.servletProject.librarySystem.utils;

import com.servletProject.librarySystem.domen.ArchiveBookTransferObject;
import com.servletProject.librarySystem.domen.UserOrdersTransferObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class GeneralActionsHelper {
    public static void giveAnswerToCompletedOrders(List<UserOrdersTransferObject> listOfAllCompletedOrders, HttpSession session,
                                                   HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
        if(listOfAllCompletedOrders != null && !listOfAllCompletedOrders.isEmpty()) {
            session.setAttribute("list_of_completed_orders", listOfAllCompletedOrders);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(path);
            requestDispatcher.forward(req, resp);
        } else {
            QueryResponseUtility.sendMessage(req, resp, session, "Readable books not found.");
        }
    }

    public static void giveAnswerToArchiveBookUsage(List<ArchiveBookTransferObject> listOfAllCompletedOrders, HttpSession session,
                                                    HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
        if(listOfAllCompletedOrders != null && !listOfAllCompletedOrders.isEmpty()) {
            session.setAttribute("list_of_archive_book", listOfAllCompletedOrders);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(path);
            requestDispatcher.forward(req, resp);
        } else {
            QueryResponseUtility.sendMessage(req, resp, session, "Archive is empty.");
        }
    }
}