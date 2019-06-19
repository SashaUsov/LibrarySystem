package com.servletProject.librarySystem.dao;

import com.servletProject.librarySystem.dao.queries.LibrarianDaoQueries;
import com.servletProject.librarySystem.dao.transactionManager.TransactionManager;
import com.servletProject.librarySystem.dao.transactionManager.WrapConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LibrarianDao {

    public void cancelOrder() {

    }

    public void deleteFromOrderTable(Long copyId) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(LibrarianDaoQueries.DELETE_BOOK_COPY_BY_COPY_ID);
            preparedStatement.setLong(1, copyId);
            preparedStatement.executeUpdate();
        }
    }
}
