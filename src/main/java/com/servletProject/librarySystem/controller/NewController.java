package com.servletProject.librarySystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("new")
public class NewController {

    @GetMapping("orders")
    public String getString(String s) {
        var s1 = "Hi " + s;
        return s1;
    }
}
