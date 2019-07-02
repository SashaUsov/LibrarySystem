package com.servletProject.librarySystem.controller;

import com.servletProject.librarySystem.domen.dto.userEntity.CreateUserEntityModel;
import com.servletProject.librarySystem.service.UserControllerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("registration")
public class RegisterController {

    private final UserControllerService userControllerService;

    public RegisterController(UserControllerService userControllerService) {
        this.userControllerService = userControllerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody CreateUserEntityModel model) {
        userControllerService.save(model);
    }
}
