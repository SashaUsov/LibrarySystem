package com.servletProject.librarySystem.facade;

import com.servletProject.librarySystem.exception.DataIsNotCorrectException;
import com.servletProject.librarySystem.service.AdminControllerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
public class AdminController {

    private final AdminControllerService adminControllerService;

    public AdminController(AdminControllerService adminControllerService) {
        this.adminControllerService = adminControllerService;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    public void grantRole(Long idUser, String role) {
        if (isParamExist(idUser, role)) {
            adminControllerService.addUserRole(idUser, role);
        } else throw new DataIsNotCorrectException("Check the correctness of the entered data and try again.");
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    public void revokeRole(Long idUser, String role) {
        if (isParamExist(idUser, role)) {
            adminControllerService.removeUserRole(idUser, role);
        } else throw new DataIsNotCorrectException("Check the correctness of the entered data and try again.");
    }

    private boolean isParamExist(Long id, String role) {
        return (id != null && role != null && !role.isEmpty());
    }
}
