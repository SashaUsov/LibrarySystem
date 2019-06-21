package com.servletProject.librarySystem.dao;

import com.servletProject.librarySystem.dao.queries.LibrarianDaoQueries;
import com.servletProject.librarySystem.dao.transactionManager.TransactionManager;
import com.servletProject.librarySystem.dao.transactionManager.WrapConnection;
import com.servletProject.librarySystem.domen.ArchiveBookUsage;
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
            return getCompletedOrdersList(resultSet);
        }
    }

    public List<CompletedOrders> findAllCompletedOrders() throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(LibrarianDaoQueries.FIND_ALL_COMPLETED_ORDERS);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getCompletedOrdersList(resultSet);
        }
    }

    public void putBookInUsageArchive(Long copyId, Long readerId, String condition) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(LibrarianDaoQueries.PUT_THE_BOOK_IN_USAGE_ARCHIVE);
            long id = DaoUtil.getNextUserId(LibrarianDaoQueries.GET_NEXT_USAGE_ID);

            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, readerId);
            preparedStatement.setLong(3, copyId);
            preparedStatement.setString(4, condition);
            preparedStatement.executeUpdate();
        }
    }

    public void updateAvailabilityAndConditionOfCopy(Long copyId, String condition, boolean availability) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(LibrarianDaoQueries.UPDATE_BOOK_COPY_INFO);
            preparedStatement.setBoolean(1, availability);
            preparedStatement.setString(2, condition);
            preparedStatement.setLong(3, copyId);
            preparedStatement.executeUpdate();
        }
    }

    public void deleteFromCompletedOrdersByCopyId(Long copyId) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(LibrarianDaoQueries.DELETE_COMPLETED_ORDER_BY_COPY_ID);
            preparedStatement.setLong(1, copyId);
            preparedStatement.executeUpdate();
        }
    }

    public List<ArchiveBookUsage> findAllUsageBooksByUserId(long userId) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(LibrarianDaoQueries.
                                                                                      FIND_ALL_ARCHIVE_BOOK_USAGE_BY_USER_ID);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getArchiveBookUsage(resultSet);
        }
    }

    public List<ArchiveBookUsage> findAllUsageBooksArchive() throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(LibrarianDaoQueries.FIND_ALL_ARCHIVE_BOOK_USAGE);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getArchiveBookUsage(resultSet);
        }
    }

    private List<CompletedOrders> getCompletedOrdersList(ResultSet resultSet) throws SQLException {
        List<CompletedOrders> completedOrders = new ArrayList<>();
        if (resultSet.next()) {
            completedOrders = DaoUtil.createCompletedOrders(resultSet);
        }
        return completedOrders;
    }

    private List<ArchiveBookUsage> getArchiveBookUsage(ResultSet resultSet) throws SQLException {
        List<ArchiveBookUsage> archiveBookUsages = new ArrayList<>();
        if (resultSet.next()) {
            archiveBookUsages = DaoUtil.createArchiveUsage(resultSet);
        }
        return archiveBookUsages;
    }
}