package com.servletProject.librarySystem.domen.dto.userEntity;

import com.servletProject.librarySystem.domen.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserEntityModel {

    private long id;
    private String firstName;
    private String lastName;
    private String mail;
    private String nickName;
    private String address;
    private List<Role> roles = new ArrayList<>();
    private boolean permissionToOrder;
    private boolean login;

}

