package com.servletProject.librarySystem.controller.adminActions;

import com.servletProject.librarySystem.service.AdminControllerService;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/admin/action")
public class ChangeUserRole extends HttpServlet {
    private AdminControllerService adminControllerService = new AdminControllerService(userRepository, userRoleRepository);

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp)
            throws ServletException, IOException {
        if (request != null) {
            long id = Long.valueOf(request.getParameter("user_id"));
            HttpSession session = request.getSession();
            if (adminControllerService.isUserExist(id)) {
                addNewUserRole(request, resp, id, session);
            } else {
                sendAnswer(request, resp, session, "User already has this role");
            }
        }
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        if (request != null) {
            long id = Long.valueOf(request.getParameter("user_id"));
            HttpSession session = request.getSession();
            if (adminControllerService.isUserExist(id)) {
                String role = request.getParameter("role");
                adminControllerService.removeUserRole(id, role);
                sendAnswer(request, resp, session, "Roles successfully revoked");
            } else {
                sendAnswer(request, resp, session, "User already has this role");
            }
        }
    }

    private void sendAnswer(HttpServletRequest request, HttpServletResponse resp,
                            HttpSession session, String message)
            throws ServletException, IOException {
        session.setAttribute("message", message);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/messagepage");
        requestDispatcher.forward(request, resp);
    }

    private void addNewUserRole(HttpServletRequest request, HttpServletResponse resp, long id, HttpSession session) throws SQLException, ServletException, IOException {
        String role = request.getParameter("role");
        adminControllerService.addUserRole(id, role);
        session.setAttribute("message", "User role added successfully");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/messagepage");
        requestDispatcher.forward(request, resp);
    }
}
