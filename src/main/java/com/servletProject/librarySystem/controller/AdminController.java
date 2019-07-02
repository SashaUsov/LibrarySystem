package com.servletProject.librarySystem.controller;

import com.servletProject.librarySystem.exception.DataIsNotCorrectException;
import com.servletProject.librarySystem.service.AdminControllerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/role")
public class AdminController {

    private final AdminControllerService adminControllerService;

    public AdminController(AdminControllerService adminControllerService) {
        this.adminControllerService = adminControllerService;
    }

    @PutMapping("grant/{id_user}/{role}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void grantRole(@PathVariable("id_user")Long id, @PathVariable("role")String role) {
        if (isParamExist(id, role)) {
            adminControllerService.addUserRole(id, role);
        } else throw new DataIsNotCorrectException("Check the correctness of the entered data and try again.");
    }

    @PutMapping("revoke/{id_user}/{role}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void revokeRole(@PathVariable("id_user")Long id, @PathVariable("role")String role) {
        if (isParamExist(id, role)) {
            adminControllerService.removeUserRole(id, role);
        } else throw new DataIsNotCorrectException("Check the correctness of the entered data and try again.");
    }

    private boolean isParamExist(Long id, String role) {
        return (id != null && role != null && !role.isEmpty());
    }
}
