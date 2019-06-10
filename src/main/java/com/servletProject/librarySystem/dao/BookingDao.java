package com.servletProject.librarySystem.dao;

import com.servletProject.librarySystem.dao.queries.BookOrderDaoQueries;
import com.servletProject.librarySystem.dao.transactionManager.TransactionManager;
import com.servletProject.librarySystem.dao.transactionManager.WrapConnection;
import com.servletProject.librarySystem.domen.CopiesOfBooks;
import com.servletProject.librarySystem.utils.DomainModelUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class BookingDao {

    public boolean isAvailable(long id) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(BookOrderDaoQueries.FIND_AVAILABILITY_OF_BOOK_COPY_BY_COPY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("availability");
            } else throw new SQLException();
        }
    }

    public void updateAvailabilityOfCopy(boolean availability, long id) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(BookOrderDaoQueries.UPDATE_AVAILABILITY_OF_BOOK_COPY);
            preparedStatement.setBoolean(1, availability);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        }
    }

    public CopiesOfBooks findAvailableBookCopyByCopyId(long copyId) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(BookOrderDaoQueries.FIND_BOOK_COPY_BY_ID_AND_AVAILABILITY);
            preparedStatement.setLong(1, copyId);
            preparedStatement.setBoolean(2, true);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Map<String, String> copyBookParameterMap = DomainModelUtil.createCopyBookParametrMap(resultSet);
                return DomainModelUtil.createBookCopyFromMap(copyBookParameterMap);
            } else throw new SQLException();
        }
    }
}
