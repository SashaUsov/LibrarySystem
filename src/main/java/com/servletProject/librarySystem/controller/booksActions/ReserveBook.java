package com.servletProject.librarySystem.controller.booksActions;

import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.OnlineOrderBookModel;
import com.servletProject.librarySystem.service.BookingService;
import com.servletProject.librarySystem.utils.BookingUtil;
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


@WebServlet("/booking")
public class ReserveBook extends HttpServlet {
    private final BookingService bookingService = new BookingService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");
        long readerId = user.getId();
        Long copyId = Long.valueOf(request.getParameter("book_copy_id"));
        if (copyId != null) {
            try {
                bookingService.reserveABook(copyId, readerId);
                response.setStatus(202);
                QueryResponseUtility.sendMessage(request, response, session, "Book successfully reserved!");
            } catch (SQLException e) {
                response.setStatus(500);
            }
        } else {
            response.setStatus(422);
            QueryResponseUtility.sendMessage(request, response, session, "Please repeat the whole procedure first");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");
        final long userId = user.getId();
        try {
            List<OnlineOrderBookModel> listOfReservedBooks = bookingService.getListOfReservedBooksByUser(userId);
            BookingUtil.getReserveListAnswer(listOfReservedBooks, request, response, session, "/user-orders");
        } catch (SQLException e) {
            response.setStatus(500);
        }
    }
}
