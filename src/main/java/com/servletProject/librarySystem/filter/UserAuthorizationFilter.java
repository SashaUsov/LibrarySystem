package com.servletProject.librarySystem.filter;

import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.utils.FilterUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebFilter(urlPatterns = {"/userpage", "/books", "/booking"})
public class UserAuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest)request).getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");

        if (user == null) {
            FilterUtil.redirectOnAuthorization(request, response);
        }
        else if (user.isLogin()) {
            ifUserIsLoggedIn(request, response, chain, user);
        } else {
            FilterUtil.redirectOnAuthorization(request, response);
        }
    }

    private void ifUserIsLoggedIn(ServletRequest request, ServletResponse response,
                                  FilterChain chain, UserEntity user)
            throws IOException, ServletException {
        if (FilterUtil.hasAnyRole(user)) {
            List<String> roles = user.getRole();
            long accessLevel = FilterUtil.getAccessLevel(roles);
            if (accessLevel > 0) {
                chain.doFilter(request, response);
            } else {
                FilterUtil.redirectOnAuthorization(request, response);
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
