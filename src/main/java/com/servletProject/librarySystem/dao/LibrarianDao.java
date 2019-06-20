package com.servletProject.librarySystem.dao;

import com.servletProject.librarySystem.dao.queries.LibrarianDaoQueries;
import com.servletProject.librarySystem.dao.transactionManager.TransactionManager;
import com.servletProject.librarySystem.dao.transactionManager.WrapConnection;
import com.servletProject.librarySystem.domen.CompletedOrders;
import com.servletProject.librarySystem.utils.DaoUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibrarianDao {

    public void deleteFromOrderTable(Long copyId) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(LibrarianDaoQueries.DELETE_BOOK_COPY_BY_COPY_ID);
            preparedStatement.setLong(1, copyId);
            preparedStatement.executeUpdate();
        }
    }

    public void deleteOrderByCopyIdAndUserId(Long copyId, Long userId) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(LibrarianDaoQueries.DELETE_ORDER_BY_COPY_ID_AND_USER_ID);
            preparedStatement.setLong(1, copyId);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
        }
    }

    public void giveBookToTheReader(Long copyId, Long userId, Long librarianId) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(LibrarianDaoQueries.GIVE_BOOK_TO_THE_USER);
            long id = DaoUtil.getNextUserId(LibrarianDaoQueries.GET_NEXT_ID);

            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, userId);
            preparedStatement.setLong(3, librarianId);
            preparedStatement.setLong(4, copyId);
            preparedStatement.executeUpdate();
        }
    }

    public List<CompletedOrders> findAllCompletedOrdersCopyIdByUserId(long userId) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(LibrarianDaoQueries.FIND_ALL_COMPLETED_ORDERS_BY_READER_ID);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<CompletedOrders> completedOrders = new ArrayList<>();
            if (resultSet.next()) {
                completedOrders = DaoUtil.createCompletedOrders(resultSet);
            }
            return completedOrders;
        }
    }
}
