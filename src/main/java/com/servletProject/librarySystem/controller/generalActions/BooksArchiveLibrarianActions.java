package com.servletProject.librarySystem.controller.generalActions;

import com.servletProject.librarySystem.domen.ArchiveBookTransferObject;
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

@WebServlet("/archive")
public class BooksArchiveLibrarianActions extends HttpServlet {
    private final LibrarianService librarianService = new LibrarianService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request != null) {
            try {
                String readerEmail = request.getParameter("email");
                getArchiveBookUsageListByUser(request, response, request.getSession(), readerEmail, "/archive-list");
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
            try {
                getFullArchiveBookUsageList(request, response, request.getSession(), "/archive-list");
            } catch (SQLException e) {
                response.setStatus(500);
            } catch (NullPointerException e) {
                QueryResponseUtility.redirectOnAuthorization(request, response);
            }
        }
    }

    private void getArchiveBookUsageListByUser(HttpServletRequest request, HttpServletResponse response,
                                               HttpSession session, String readerEmail, String path)
            throws SQLException, ServletException, IOException {
        List<ArchiveBookTransferObject> listOfCompletedOrders = librarianService.getListOfArciveUsageByUser(readerEmail);
        GeneralActionsHelper.giveAnswerToArchiveBookUsage(listOfCompletedOrders, session, request, response, path);
    }

    private void getFullArchiveBookUsageList(HttpServletRequest request, HttpServletResponse response,
                                             HttpSession session, String path)
            throws SQLException, ServletException, IOException {
        List<ArchiveBookTransferObject> listOfCompletedOrders = librarianService.getListOfAllArchiveUsage();
        GeneralActionsHelper.giveAnswerToArchiveBookUsage(listOfCompletedOrders, session, request, response, path);
    }
}
