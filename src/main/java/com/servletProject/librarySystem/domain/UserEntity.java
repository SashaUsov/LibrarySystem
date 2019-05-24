package com.servletProject.librarySystem.domain;

import com.sun.istack.internal.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class UserEntity {

    private long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String nickName;
    @NotNull
    private String password;
    @NotNull
    private Date birthday;
    private String gender;
    @NotNull
    private String mail;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String address;
    private String role;
    private boolean permissionToOrder;
}
