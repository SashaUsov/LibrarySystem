package com.servletProject.librarySystem.utils;

import com.servletProject.librarySystem.domen.UserEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DomainModelUtil {

    public static UserEntity createUserEntity(ResultSet resultSet) throws SQLException {

        UserEntity userFromDb = new UserEntity();

        userFromDb.setId(resultSet.getLong("id"));
        userFromDb.setFirstName(resultSet.getString("first_name"));
        userFromDb.setLastName(resultSet.getString("last_name"));
        userFromDb.setNickName(resultSet.getString("nick_name"));
        userFromDb.setMail(resultSet.getString("mail"));
        userFromDb.setAddress(resultSet.getString("address"));
        userFromDb.setPermissionToOrder(resultSet.getBoolean("permission_to_order"));

        return userFromDb;
    }

}
