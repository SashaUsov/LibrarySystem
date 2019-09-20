package com.servletProject.librarySystem.domen.dto.userEntity;

import com.servletProject.librarySystem.domen.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserEntityModel {
    private long id;
    private String firstName;
    private String lastName;
    private String mail;
    private String nickName;
    private String address;
    private Set<Role> roles = new HashSet<>();
    private boolean active;
    private boolean login;
}

