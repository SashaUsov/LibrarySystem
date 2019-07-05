package com.servletProject.librarySystem.facade;

import com.servletProject.librarySystem.domen.dto.userEntity.CreateUserEntityModel;
import com.servletProject.librarySystem.domen.dto.userEntity.UserEntityModel;
import com.servletProject.librarySystem.exception.UserNotFoundException;
import com.servletProject.librarySystem.service.UserControllerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Service
public class UserFacade {

    private final UserControllerService userControllerService;

    public UserFacade(UserControllerService userControllerService) {
        this.userControllerService = userControllerService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(CreateUserEntityModel model) {
        userControllerService.save(model);
    }

    public UserEntityModel getUserByNickName(String nickName) {
        if (nickName != null && !nickName.isEmpty()) {
            return userControllerService.getUserByNickName(nickName);
        } else throw new UserNotFoundException("User with this nickname does not exist. Sign in and try again.");
    }
}
