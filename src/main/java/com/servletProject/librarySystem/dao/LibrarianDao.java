package com.servletProject.librarySystem.dao;

import com.servletProject.librarySystem.dao.queries.LibrarianDaoQueries;
import com.servletProject.librarySystem.dao.transaction.TransactionManager;
import com.servletProject.librarySystem.dao.transaction.WrapConnection;
import com.servletProject.librarySystem.utils.DaoUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class LibrarianDao {

    public Long findBookIdByBookTitle(String bookTitle) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(LibrarianDaoQueries.FIND_BOOK_ID_BY_BOOK_TITLE);
            preparedStatement.setString(1, bookTitle);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                long id = resultSet.getLong("id");
                return id;
            } else {
                return null;
            }
        }
    }

    public void saveBook(Map<String, String> paramMap) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(LibrarianDaoQueries.SAVE_BOOK);
            long id = DaoUtil.getNextUserId(LibrarianDaoQueries.GET_NEXT_BOOK_ID);

            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, paramMap.get("book_title"));
            preparedStatement.setString(3, paramMap.get("book_author"));
            preparedStatement.setLong(4, Long.valueOf(paramMap.get("publication")));
            preparedStatement.setString(5, paramMap.get("genre"));
            preparedStatement.executeUpdate();

            saveBookCopy(id, false);
        }
    }

    public void saveBookCopy(long bookId, boolean incrementTotalAmount) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(LibrarianDaoQueries.SAVE_BOOK_COPY);
            long id = DaoUtil.getNextUserId(LibrarianDaoQueries.GET_NEXT_BOOK_COPY_ID);
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, bookId);
            preparedStatement.setString(3, "good");
            preparedStatement.executeUpdate();
        }
        if (incrementTotalAmount) {
            incrementBookTotalAmount(bookId);
        }
    }

    private void incrementBookTotalAmount(long bookId) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(LibrarianDaoQueries.UPDATE_INCREMENT_BOOK_TOTAL_AMOUNT);

            preparedStatement.setLong(1, bookId);
            preparedStatement.executeUpdate();
        }
    }

    public List<Map<String, String>> getAllBook() throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(LibrarianDaoQueries.FIND_ALL_BOOK_FROM_CATALOG);
            final ResultSet resultSet = preparedStatement.executeQuery();
            List<Map<String, String>> bookCatalog = DaoUtil.createBookCatalog(resultSet);
            if (bookCatalog != null && !bookCatalog.isEmpty()) {
                return bookCatalog;
            } else return null;
        }
    }


    private void decrementBookTotalAmount(long bookId) throws SQLException {

        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(LibrarianDaoQueries.UPDATE_DECREMENT_BOOK_TOTAL_AMOUNT);

            preparedStatement.setLong(1, bookId);
            preparedStatement.executeUpdate();
        }
    }

    private Long findTotalAmountByBookId(long bookId) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(LibrarianDaoQueries.FIND_TOTAL_AMOUNT_BY_BOOK_ID);
            preparedStatement.setLong(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            long totalAmount = 0;
            if (resultSet.next()) {
                totalAmount = resultSet.getLong("id");
            }
            return totalAmount;
        }
    }
}
