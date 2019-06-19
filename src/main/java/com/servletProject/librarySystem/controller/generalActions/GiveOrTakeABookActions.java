package com.servletProject.librarySystem.controller.generalActions;

import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.service.LibrarianService;
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
    private final LibrarianService librarianService = new LibrarianService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request != null) {
            final HttpSession session = request.getSession();
            try {
                Long userId = Long.valueOf(request.getParameter("reader_id"));
                Long copyId = Long.valueOf(request.getParameter("book_copy_id"));
                UserEntity user = (UserEntity) session.getAttribute("user");
                librarianService.giveBookToTheReader(copyId, userId, user.getId());
                QueryResponseUtility.sendMessage(request, response, session, "Order completed successfully!");
            } catch (SQLException e) {
                response.setStatus(500);
            }
        }
    }
}