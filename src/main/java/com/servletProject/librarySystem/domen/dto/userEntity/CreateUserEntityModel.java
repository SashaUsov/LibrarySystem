package com.servletProject.librarySystem.domen.dto.userEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
public class CreateUserEntityModel {

    @NotNull
    @NotEmpty(message = "The first name cannot be empty")
    private String firstName;

    @NotNull
    @NotEmpty(message = "The nick name cannot be empty")
    private String nickName;

    @NotNull
    @NotEmpty(message = "The last name cannot be empty")
    private String lastName;

    @NotNull
    @NotEmpty(message = "Enter correct password")
    @Length(max = 15, message = "Password to long")
    private String password;

    @NotNull(message = "Email cannot be empty")
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid" )
    private String mail;

    @NotNull
    @NotEmpty
    private String address;

}
