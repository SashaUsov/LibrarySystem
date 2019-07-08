package com.servletProject.librarySystem.facade;

import com.servletProject.librarySystem.converter.UserEntityConverter;
import com.servletProject.librarySystem.domen.dto.userEntity.CreateUserEntityModel;
import com.servletProject.librarySystem.domen.dto.userEntity.UserEntityModel;
import com.servletProject.librarySystem.exception.UserNotFoundException;
import com.servletProject.librarySystem.service.data.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Service
public class UserFacade {

    private final UserService userService;

    public UserFacade(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(CreateUserEntityModel model) {
        userService.save(model);
    }

    public UserEntityModel getUserByNickName(String nickName) {
        if (nickName != null && !nickName.isEmpty()) {
            return UserEntityConverter.toModel(userService.getUserByNickName(nickName));
        } else throw new UserNotFoundException("User with this nickname does not exist. Sign in and try again.");
    }
}
