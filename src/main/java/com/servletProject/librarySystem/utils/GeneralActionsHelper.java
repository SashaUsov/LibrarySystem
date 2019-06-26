package com.servletProject.librarySystem.utils;

import com.servletProject.librarySystem.domen.dto.ArchiveBookTransferObject;
import com.servletProject.librarySystem.domen.CopiesOfBooks;
import com.servletProject.librarySystem.domen.dto.UserOrdersTransferObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class GeneralActionsHelper {
    public static void giveAnswerToCompletedOrders(List<UserOrdersTransferObject> listOfAllCompletedOrders, HttpSession session,
                                                   HttpServletRequest req, HttpServletResponse resp, String path)
            throws ServletException, IOException {
        if(listOfAllCompletedOrders != null && !listOfAllCompletedOrders.isEmpty()) {
            session.setAttribute("list_of_completed_orders", listOfAllCompletedOrders);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(path);
            requestDispatcher.forward(req, resp);
        } else {
            QueryResponseUtility.sendMessage(req, resp, session, "Readable books not found.");
        }
    }

    public static void giveAnswerToArchiveBookUsage(List<ArchiveBookTransferObject> listOfAllCompletedOrders, HttpSession session,
                                                    HttpServletRequest req, HttpServletResponse resp, String path)
            throws ServletException, IOException {
        if(listOfAllCompletedOrders != null && !listOfAllCompletedOrders.isEmpty()) {
            session.setAttribute("list_of_archive_book", listOfAllCompletedOrders);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(path);
            requestDispatcher.forward(req, resp);
        } else {
            QueryResponseUtility.sendMessage(req, resp, session, "Archive is empty.");
        }
    }

    public static void giveAnswerToUnusableRequest(List<CopiesOfBooks> copiesOfBooksList, HttpSession session,
                                                    HttpServletRequest req, HttpServletResponse resp, String path)
            throws ServletException, IOException {
        if(copiesOfBooksList != null && !copiesOfBooksList.isEmpty()) {
            session.setAttribute("unusable_copy", copiesOfBooksList);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(path);
            requestDispatcher.forward(req, resp);
        } else {
            QueryResponseUtility.sendMessage(req, resp, session, "All books are in good condition :)");
        }
    }
}