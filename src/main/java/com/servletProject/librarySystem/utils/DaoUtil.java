package com.servletProject.librarySystem.utils;

import com.servletProject.librarySystem.dao.transaction.TransactionManager;
import com.servletProject.librarySystem.dao.transaction.WrapConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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

    public static List<Map<String, String>> createBookCatalog(ResultSet resultSet) throws SQLException {
        List<Map<String, String>> bookCatalod = new ArrayList<>();
        while (resultSet.next()) {
            Map<String, String> book = new HashMap<>();
            book.put("id", String.valueOf(resultSet.getLong("id")));
            book.put("bookTitle", resultSet.getString("book_title"));
            book.put("bookAuthor", resultSet.getString("book_author"));
            book.put("yearOfPublication", String.valueOf(resultSet.getLong("publication")));
            book.put("genre", resultSet.getString("id"));
            book.put("totalAmount", String.valueOf(resultSet.getLong("total_amount")));

            bookCatalod.add(book);
        }
        return bookCatalod;
    }
}
