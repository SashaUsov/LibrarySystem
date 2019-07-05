package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.converter.UserEntityConverter;
import com.servletProject.librarySystem.domen.Role;
import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.domen.dto.userEntity.CreateUserEntityModel;
import com.servletProject.librarySystem.domen.dto.userEntity.UserEntityModel;
import com.servletProject.librarySystem.service.data.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserControllerService {

    private final UserService userService;

    public UserControllerService(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public void save(CreateUserEntityModel model) {

        userService.checkIfTheUserExists(model.getMail());

        UserEntity entity = UserEntityConverter.toEntity(model);
        entity.setActive(true);
        entity.setPermission(true);
        entity.getRoles().add(Role.USER);

        userService.save(entity);
    }

    public UserEntityModel findUser(Long id) {
        return UserEntityConverter.toModel(userService.getUserIfExist(id));
    }

    public UserEntityModel getUserByNickName(String nickName) {
        return UserEntityConverter.toModel(userService.getUserByNickName(nickName));
    }
}
