package com.servletProject.librarySystem.controller.booksActions;

import com.servletProject.librarySystem.service.LibrarianService;
import com.servletProject.librarySystem.utils.QueryResponseUtility;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/order-cancel")
public class CancelOrderAction extends HttpServlet {
    private final LibrarianService librarianService = new LibrarianService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            Long copyId = Long.valueOf(req.getParameter("copy_id"));
        if (copyId != null) {
            try {
                librarianService.cancelOrder(copyId);
                QueryResponseUtility.sendMessage(req,resp, req.getSession(), "Order canceled successfully!");
            } catch (SQLException e) {
                QueryResponseUtility.sendMessage(req,resp, req.getSession(), "Something went wrong :(");
            }
        } else {
            QueryResponseUtility.sendMessage(req,resp, req.getSession(), "This action is impossible!");
        }
    }
}
