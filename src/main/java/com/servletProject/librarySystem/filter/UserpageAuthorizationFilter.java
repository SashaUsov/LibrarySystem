package com.servletProject.librarySystem.filter;

import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.utils.FilterUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebFilter("/userpage")
public class UserpageAuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest)request).getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");

        if (user == null) {
            redirectOnAuthorization(request, response);
        }
        else if (user.isLogin()) {
            ifUserIsLoggedIn(request, response, chain, user);
        } else {
            redirectOnAuthorization(request, response);
        }
    }

    private void ifUserIsLoggedIn(ServletRequest request, ServletResponse response,
                                  FilterChain chain, UserEntity user)
            throws IOException, ServletException {
        if (hasAnyRole(user)) {
            List<String> roles = user.getRole();
            long accessLevel = FilterUtil.getAccessLevel(roles);
            if (accessLevel > 0) {
                sendToTheView(request, response, chain);
            } else {
                redirectOnAuthorization(request, response);
            }
        }
    }

    private void redirectOnAuthorization(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/authorization");
        requestDispatcher.forward(request, response);
    }

    private void sendToTheView(ServletRequest request,
                               ServletResponse response,
                               FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }

    private boolean hasAnyRole(UserEntity user) {
        return user.getRole() != null && !user.getRole().isEmpty();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
