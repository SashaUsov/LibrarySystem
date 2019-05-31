package com.servletProject.librarySystem.dao;

import com.servletProject.librarySystem.dao.transaction.TransactionManager;
import com.servletProject.librarySystem.dao.transaction.WrapConnection;
import com.servletProject.librarySystem.domen.Role;
import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.utils.DomainModelUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserRoleDao {

    public void setDefaultRole(long user_id) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user_role (id, user_id, user_role) VALUES (?, ?, ?)");
            long id = getNextUserId();

            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, user_id);
            preparedStatement.setString(3, Role.USER.toString());
            preparedStatement.executeUpdate();
        }
    }

    private long getNextUserId() throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT nextval('seq_user_role_id')");
            final ResultSet resultSet = preparedStatement.executeQuery();
            Long id = null;
            if (resultSet.next()) {
                id = resultSet.getLong("nextval");
            }
            return id;
        }
    }
}
