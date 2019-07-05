package com.servletProject.librarySystem.controller;

import com.servletProject.librarySystem.domen.dto.userEntity.CreateUserEntityModel;
import com.servletProject.librarySystem.facade.UserFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final UserFacade userFacade;

    public RegistrationController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping("/registration")
    public String registerPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid CreateUserEntityModel user) {
        userFacade.createUser(user);

        return "redirect:/login";
    }
}
