package com.servletProject.librarySystem.controller;

import com.servletProject.librarySystem.exception.DataIsNotCorrectException;
import com.servletProject.librarySystem.facade.AdminFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminFacade adminFacade;

    public AdminController(AdminFacade adminFacade) {
        this.adminFacade = adminFacade;
    }

    @GetMapping
    public String getAdminPge() {
        return "admin";
    }

    @PostMapping("grant")
    public String grantRole(Principal principal,
                            @RequestParam String nickName,
                            @RequestParam String role
    ) {
        String access = principal.getName();
        if (isValidData(nickName, access)) {
            adminFacade.grantRole(nickName, access, role);
            return "admin";
        } else throw new DataIsNotCorrectException("Enter correct data and try again.");
    }

    @PostMapping("revoke")
    public String revokeRole(Principal principal,
                             @RequestParam String nickName,
                             @RequestParam String role
    ) {
        String access = principal.getName();
        if (isValidData(nickName, access)) {
            adminFacade.revokeRole(nickName, access, role);
            return "admin";
        } else throw new DataIsNotCorrectException("Enter correct data and try again.");
    }

    private boolean isValidData(String nickName, String admin) {
        return nickName != null && !nickName.isEmpty() && admin != null && !admin.isEmpty();
    }
}
