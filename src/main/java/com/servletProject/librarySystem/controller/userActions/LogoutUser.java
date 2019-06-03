package com.servletProject.librarySystem.controller.userActions;

import com.servletProject.librarySystem.domen.UserEntity;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout/exit")
public class LogoutUser extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req != null) {
            HttpSession session = req.getSession();
            final UserEntity user = (UserEntity) session.getAttribute("user");
            if (user.isLogin()) {
                session.setAttribute("user", null);
                session.setAttribute("message", "Logout successful!");
            }else {
                session.setAttribute("message", "You are not logged in the system!");
            }
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/messagepage");
            requestDispatcher.forward(req, resp);
        }
    }
}
