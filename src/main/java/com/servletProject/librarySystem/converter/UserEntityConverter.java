package com.servletProject.librarySystem.converter;

import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.domen.dto.userEntity.CreateUserEntityModel;
import com.servletProject.librarySystem.domen.dto.userEntity.UserEntityModel;

public class UserEntityConverter {

    public static UserEntity toEntity(CreateUserEntityModel model) {
        UserEntity entity = new UserEntity();
        entity.setFirstName(model.getFirstName());
        entity.setLastName(model.getLastName());
        entity.setNickName(model.getNickName());
        entity.setPassword(model.getPassword());
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
        model.setPermissionToOrder(entity.isPermissionToOrder());
        model.setAddress(entity.getAddress());
        model.setLogin(entity.isLogin());
        model.setRoles(entity.getRoles());

        return model;
    }
}
