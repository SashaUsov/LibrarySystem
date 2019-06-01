package com.servletProject.librarySystem.utils;

import com.servletProject.librarySystem.dao.transaction.TransactionManager;
import com.servletProject.librarySystem.dao.transaction.WrapConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoUtil {

    public static long getNextUserId(String sqlQueri) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQueri);
            final ResultSet resultSet = preparedStatement.executeQuery();
            Long id = null;
            if (resultSet.next()) {
                id = resultSet.getLong("nextval");
            }
            return id;
        }
    }
}
