package com.servletProject.librarySystem.dao;

import com.servletProject.librarySystem.dao.transaction.TransactionManager;
import com.servletProject.librarySystem.dao.transaction.WrapConnection;
import com.servletProject.librarySystem.dto.UserEntity;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class UserDao {
    private Properties dbProps;

    public UserDao() {
        loadPropertyFile();
    }

    public UserEntity findUserByNickName(String nickName) throws SQLException {
        WrapConnection connection = TransactionManager.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("FIND_USER_BY_NICK_NAME");
            preparedStatement.setString(1, nickName);
            ResultSet resultSet = preparedStatement.executeQuery();
            UserEntity user = null;

            if (resultSet.next()) {
                user = createUserEntity(resultSet);
            }
            return user;
        }finally{
            connection.close();
        }
    }

    public UserEntity save(UserEntity user) throws SQLException {

        WrapConnection connection = TransactionManager.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SAVE_USER");
            preparedStatement.setLong(1, user.getId()); //////????????
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getNickName());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getGender());
            preparedStatement.setString(7, user.getMail());
            preparedStatement.setString(8, user.getPhoneNumber());
            preparedStatement.setString(9, user.getAddress());
            preparedStatement.setString(10, user.getRole());
            preparedStatement.execute();
            return findUserByNickName(user.getNickName());
        } finally{
            connection.close();
        }
    }

    private UserEntity createUserEntity(ResultSet resultSet) throws SQLException {
//        final UserEntity buildUser = UserEntity.builder()
//                .firstName(resultSet.getString("first_name"))
//                .lastName(resultSet.getString("last_name"))
//                .nickName(resultSet.getString("nick_name"))
//                .password(resultSet.getString("password"))
//                .gender(resultSet.getString("gender"))
//                .address(resultSet.getString("address"))
//                .mail(resultSet.getString("mail"))
//                .id(resultSet.getLong("id"))
//                .phoneNumber(resultSet.getString("phone_number"))
//                .permissionToOrder(resultSet.getBoolean("permission_to_order"))
//                .role(resultSet.getString("role"))
//                .build();
//        return buildUser;
    return null;
    }

    private void loadPropertyFile() {
        try (FileInputStream in = new FileInputStream("/Users/samsonov/IdeaProjects/LibrarySystem" +
                                                              "/src/main/resources/sql-userDao-request.properties")) {
            dbProps  = new Properties();
            dbProps.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbProps.getProperty("FIND_USER_BY_NICK_NAME");
    }


}
