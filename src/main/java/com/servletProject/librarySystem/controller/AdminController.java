package com.servletProject.librarySystem.controller;

import com.servletProject.librarySystem.domen.dto.GrantRoleModel;
import com.servletProject.librarySystem.facade.AdminFacade;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final AdminFacade adminFacade;

    public AdminController(AdminFacade adminFacade) {
        this.adminFacade = adminFacade;
    }

    @PostMapping("grant")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void grantRole(@Valid @RequestBody GrantRoleModel roleModel, Principal principal) {
        var access = principal.getName();
        adminFacade.grantRole(roleModel.getNickName(), access, roleModel.getRole());
    }

    @PostMapping("revoke-role")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void revokeRole(@Valid @RequestBody GrantRoleModel roleModel, Principal principal) {
        var access = principal.getName();
        adminFacade.revokeRole(roleModel.getNickName(), access, roleModel.getRole());
    }
}
