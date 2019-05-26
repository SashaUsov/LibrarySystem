package com.servletProject.librarySystem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
//@Builder
public class UserEntity {

    public UserEntity() {
    }

    private long id;
    private String firstName;
    private String lastName;
    private String nickName;
    private String password;
    private String gender;
    private String mail;
    private String phoneNumber;
    private String address;
    private String role;
    private boolean permissionToOrder;
}

