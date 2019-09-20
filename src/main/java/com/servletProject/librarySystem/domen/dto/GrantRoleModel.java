package com.servletProject.librarySystem.domen.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.servletProject.librarySystem.domen.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
public class GrantRoleModel {

    @NotNull
    @NotEmpty
    private String nickName;

    @NotNull
    @NotEmpty
    private Role role;
}
