package com.servletProject.librarySystem.controller;

import com.servletProject.librarySystem.domen.dto.userEntity.CreateUserEntityModel;
import com.servletProject.librarySystem.facade.UserFacade;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("/registration")
public class RegistrationController {

    private final UserFacade userFacade;

    public RegistrationController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void registration(@Valid @RequestBody CreateUserEntityModel user) {
        userFacade.createUser(user);
    }
}
