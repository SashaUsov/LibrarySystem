package com.servletProject.librarySystem.domen;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class UserEntity {

    @Getter
    @Setter
    private long id;
    @Getter
    @Setter
    private String firstName;
    @Getter
    @Setter
    private String lastName;
    @Getter
    @Setter
    private String nickName;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String mail;
    @Getter
    @Setter
    private String address;
    @Getter
    @Setter
    private List<String> role = new ArrayList<>();
    @Getter
    @Setter
    private boolean permissionToOrder;

    @Getter
    @Setter
    private boolean login = false;

}

