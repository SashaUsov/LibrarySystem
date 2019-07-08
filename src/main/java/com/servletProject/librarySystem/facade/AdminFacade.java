package com.servletProject.librarySystem.facade;

import com.servletProject.librarySystem.exception.DataIsNotCorrectException;
import com.servletProject.librarySystem.service.AdminControllerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Service
public class AdminFacade {

    private final AdminControllerService adminControllerService;

    public AdminFacade(AdminControllerService adminControllerService) {
        this.adminControllerService = adminControllerService;
    }

    @Transactional
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void grantRole(String nickName, String admin, String role) {
        if (isParamExist(role)) {
            adminControllerService.addUserRole(nickName, admin, role);
        } else throw new DataIsNotCorrectException("Check the correctness of the entered data and try again.");
    }

    @Transactional
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void revokeRole(String nickName, String admin, String role) {
        if (isParamExist(role)) {
            adminControllerService.removeUserRole(nickName, admin, role);
        } else throw new DataIsNotCorrectException("Check the correctness of the entered data and try again.");
    }

    private boolean isParamExist(String role) {
        return (role != null && !role.isEmpty());
    }
}
