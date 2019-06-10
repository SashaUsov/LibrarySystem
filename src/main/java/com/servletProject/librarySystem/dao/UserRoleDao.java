package com.servletProject.librarySystem.dao;

import com.servletProject.librarySystem.dao.queries.UserRoleDaoQueries;
import com.servletProject.librarySystem.dao.transactionManager.TransactionManager;
import com.servletProject.librarySystem.dao.transactionManager.WrapConnection;
import com.servletProject.librarySystem.domen.Role;
import com.servletProject.librarySystem.utils.DaoUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDao {

    public void deleteRoleByUserIdAndRole(long id, String role) throws SQLException{
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(UserRoleDaoQueries.DELETE_BY_USER_ID_AND_ROLE);

            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, role);
            preparedStatement.executeUpdate();
        }
    }

    public void setDefaultRole(long userId) throws SQLException {
        final String role = Role.USER.toString();
        setUserRole(userId, role);
    }

    public void setUserRole(long userId, String role) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(UserRoleDaoQueries.SET_USER_ROLE);

            long id = getNextUserId();

            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, userId);
            preparedStatement.setString(3, role);
            preparedStatement.executeUpdate();
        }
    }

    public List<String> findUserRoleById(long id) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(UserRoleDaoQueries.FIND_USER_ROLE_BY_ID);
            preparedStatement.setLong(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();

            List<String> roleList = new ArrayList<>();
            while (resultSet.next()) {
                String role = resultSet.getString("role");
                roleList.add(role);
            }
            return roleList;
        }
    }

    private long getNextUserId() throws SQLException {
        return DaoUtil.getNextUserId(UserRoleDaoQueries.GET_NEXT_ID);
    }
}
