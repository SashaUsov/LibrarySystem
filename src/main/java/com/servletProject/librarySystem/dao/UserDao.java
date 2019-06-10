package com.servletProject.librarySystem.dao;

import com.servletProject.librarySystem.dao.queries.UserEntityDaoQueries;
import com.servletProject.librarySystem.dao.transactionManager.TransactionManager;
import com.servletProject.librarySystem.dao.transactionManager.WrapConnection;
import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.utils.DaoUtil;
import com.servletProject.librarySystem.utils.DomainModelUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserDao {

    public UserEntity findUserById(long id) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UserEntityDaoQueries.FIND_USER_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            UserEntity user = null;

            if (resultSet.next()) {
                user = DomainModelUtil.createUserEntity(resultSet);
                return user;
            } else {
                return null;
            }

        }
    }

    public UserEntity findUserByNickName(String nickName) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UserEntityDaoQueries.FIND_USER_BY_NICK_NAME);
            preparedStatement.setString(1, nickName);
            ResultSet resultSet = preparedStatement.executeQuery();
            UserEntity user = null;

            if (resultSet.next()) {
                user = DomainModelUtil.createUserEntity(resultSet);
                user.setPassword(resultSet.getString("password"));
            }
            return user;
        }
    }

    public UserEntity save(Map<String, String> paramMap) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UserEntityDaoQueries.SAVE_USER);
            long id = getNextUserId();

            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, paramMap.get("first_name"));
            preparedStatement.setString(3, paramMap.get("last_name"));
            preparedStatement.setString(4, paramMap.get("nick_name"));
            preparedStatement.setString(5, paramMap.get("password"));
            preparedStatement.setString(6, paramMap.get("mail"));
            preparedStatement.setString(7, paramMap.get("address"));
            preparedStatement.executeUpdate();

            return findUserById(id);
        }
    }

    private long getNextUserId() throws SQLException {
            return DaoUtil.getNextUserId(UserEntityDaoQueries.GET_NEXT_ID);
    }
}
