package com.servletProject.librarySystem.facade;

import com.servletProject.librarySystem.domen.Role;
import com.servletProject.librarySystem.exception.DataIsNotCorrectException;
import com.servletProject.librarySystem.service.AdminControllerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
public class AdminFacade {

    private final AdminControllerService adminControllerService;

    public AdminFacade(AdminControllerService adminControllerService) {
        this.adminControllerService = adminControllerService;
    }

    @Transactional
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void grantRole(String nickName, String admin, Role role) {
        if (role != null) {
            adminControllerService.addUserRole(nickName, admin, role);
        } else throw new DataIsNotCorrectException("Check the correctness of the entered data and try again.");
    }

    @Transactional
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void revokeRole(String nickName, String admin, Role role) {
        if (role != null) {
            adminControllerService.removeUserRole(nickName, admin, role);
        } else throw new DataIsNotCorrectException("Check the correctness of the entered data and try again.");
    }
}
