package com.servletProject.librarySystem.filter;

import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.utils.FilterUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebFilter("/admin")
public class AdminAuthorizationFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) request).getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");

        if (user == null) {
            FilterUtil.redirectOnAuthorization(request, response);
        } else if (user.isLogin()) {
            if (FilterUtil.hasAnyRole(user)) {
                List<String> roles = user.getRole();
                boolean isAdmin = false;
                for (String role : roles) {
                    if ("ADMIN".equals(role)) {
                        isAdmin = true;
                        chain.doFilter(request, response);
                    }
                }
                if (!isAdmin) {
                    session.setAttribute("message", "You do not have permission to access this page.");
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/messagepage");
                    requestDispatcher.forward(request, response);
                }
            }
        }
    }

    @Override
    public void destroy() {

    }
}
