package com.servletProject.librarySystem.converter;

import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.domen.dto.userEntity.CreateUserEntityModel;
import com.servletProject.librarySystem.domen.dto.userEntity.UserEntityModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserEntityConverter {

    private final static PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    public static UserEntity toEntity(CreateUserEntityModel model) {
        UserEntity entity = new UserEntity();
        entity.setFirstName(model.getFirstName());
        entity.setLastName(model.getLastName());
        entity.setNickName(model.getNickName());
        entity.setPassword(PASSWORD_ENCODER.encode(model.getPassword()));
        entity.setMail(model.getMail());
        entity.setAddress(model.getAddress());

        return entity;
    }

    public static UserEntityModel toModel(UserEntity entity) {
        UserEntityModel model = new UserEntityModel();
        model.setId(entity.getId());
        model.setFirstName(entity.getFirstName());
        model.setLastName(entity.getLastName());
        model.setNickName(entity.getNickName());
        model.setMail(entity.getMail());
        model.setActive(entity.isActive());
        model.setAddress(entity.getAddress());
        model.setLogin(entity.isPermission());
        model.setRoles(entity.getRoles());

        return model;
    }
}
