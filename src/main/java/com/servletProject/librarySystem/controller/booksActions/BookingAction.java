package com.servletProject.librarySystem.controller.booksActions;

import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.service.BooksService;
import com.servletProject.librarySystem.utils.QueryResponseUtility;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/booking")
public class BookingAction extends HttpServlet {
    private final BooksService booksService = new BooksService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request != null) {
            HttpSession session = request.getSession();
            if (session != null) {
                UserEntity user = (UserEntity)session.getAttribute("user");
                String copy_id = request.getParameter("book_copy_id");
                if (user != null && copy_id != null) {

                } else {
                    response.setStatus(422);
                    QueryResponseUtility.sendMessage(request, response, session, "Please repeat the whole procedure first");
                }
            }
        } else {
            QueryResponseUtility.redirectOnAuthorization(request, response);
        }
    }
}
