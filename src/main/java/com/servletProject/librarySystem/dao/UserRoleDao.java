package com.servletProject.librarySystem.dao;

import com.servletProject.librarySystem.dao.queries.UserRoleDaoQueries;
import com.servletProject.librarySystem.dao.transaction.TransactionManager;
import com.servletProject.librarySystem.dao.transaction.WrapConnection;
import com.servletProject.librarySystem.domen.Role;
import com.servletProject.librarySystem.utils.DaoUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDao {

    public void setDefaultRole(long user_id) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(UserRoleDaoQueries.SET_DEFAULT_ROLE);

            long id = getNextUserId();

            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, user_id);
            preparedStatement.setString(3, Role.USER.toString());
            preparedStatement.executeUpdate();
        }
    }

    private long getNextUserId() throws SQLException {
            return DaoUtil.getNextUserId(UserRoleDaoQueries.GET_NEXT_ID);
    }

    public List<String> findUserRoleById(long id) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(UserRoleDaoQueries.FIND_USER_ROLE_BY_ID);

            preparedStatement.setLong(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();

            List<String> roleList = new ArrayList<>();
            while (resultSet.next()) {
                String role = resultSet.getString("user_role");
                roleList.add(role);
            }
            return roleList;
        }
    }
}
