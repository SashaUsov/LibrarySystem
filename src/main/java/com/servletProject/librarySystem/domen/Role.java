package com.servletProject.librarySystem.domen;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    LIBRARIAN,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
