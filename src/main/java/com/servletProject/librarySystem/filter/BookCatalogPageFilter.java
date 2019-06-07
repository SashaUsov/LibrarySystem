package com.servletProject.librarySystem.filter;

import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.utils.FilterUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebFilter("/book/catalog")
public class BookCatalogPageFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user == null) {
            FilterUtil.redirectOnAuthorization(request, response);
        } else if (user.isLogin()) {
            if (FilterUtil.hasAnyRole(user)) {
                giveAccess(request, response, chain, user);
            } else {
                FilterUtil.sendMessage(request, response, session, "You do not have access to this service.");
            }
        }
    }

    private void giveAccess(ServletRequest request, ServletResponse response,
                            FilterChain chain, UserEntity user)
            throws IOException, ServletException {
        List<String> roles = user.getRole();
        for (String role : roles) {
            if ("LIBRARIAN".equals(role) || "ADMIN".equals(role)) {
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
