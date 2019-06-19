package com.servletProject.librarySystem.utils;

import com.servletProject.librarySystem.dao.transactionManager.TransactionManager;
import com.servletProject.librarySystem.dao.transactionManager.WrapConnection;
import com.servletProject.librarySystem.domen.CopiesOfBooks;
import com.servletProject.librarySystem.domen.OnlineOrderBook;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

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

    private static List<Map<String, String>> createBookCatalog(ResultSet resultSet) throws SQLException {
        List<Map<String, String>> bookCatalod = new ArrayList<>();
        do {
            Map<String, String> book = new HashMap<>();
            book.put("id", String.valueOf(resultSet.getLong("id")));
            book.put("bookTitle", resultSet.getString("book_title"));
            book.put("bookAuthor", resultSet.getString("book_author"));
            book.put("yearOfPublication", String.valueOf(resultSet.getLong("publication")));
            book.put("genre", resultSet.getString("genre"));
            book.put("totalAmount", String.valueOf(resultSet.getLong("total_amount")));

            bookCatalod.add(book);
        } while (resultSet.next());
        return bookCatalod;
    }

    public static List<Map<String, String>> createBooksCopy(ResultSet resultSet) throws SQLException {
        List<Map<String, String>> booksCopy = new ArrayList<>();
        while (resultSet.next()) {
            Map<String, String> book = DomainModelUtil.createCopyBookParameterMap(resultSet);
            booksCopy.add(book);
        }
        return booksCopy;
    }

    public static List<Map<String, String>> getBooksMaps(ResultSet resultSet) throws SQLException {
        List<Map<String, String>> bookCatalog = createBookCatalog(resultSet);
        if (bookCatalog != null && !bookCatalog.isEmpty()) {
            return bookCatalog;
        } else return null;
    }

    public static List<CopiesOfBooks> returnAvailableBooks(List<CopiesOfBooks> bookList) {
        return bookList.stream().filter(book -> book.isAvailability() == true).collect(Collectors.toList());
    }

    public static Long[] getAllBooksId(ResultSet resultSet, String columnName) throws SQLException {
        List<Long> booksIdList = new ArrayList<>();
        do {
            long id = resultSet.getLong(columnName);
            booksIdList.add(id);
        } while (resultSet.next());
        return booksIdList.stream().toArray(Long[]::new);
    }

    public static List<OnlineOrderBook> getAllOrders(ResultSet resultSet) throws SQLException {
        List<OnlineOrderBook> booksIdList = new ArrayList<>();
        do {
            OnlineOrderBook onlineOrderBook = new OnlineOrderBook();
            onlineOrderBook.setId(resultSet.getLong("id"));
            onlineOrderBook.setIdBookCopy(resultSet.getLong("book_copy_id"));
            onlineOrderBook.setIdUser(resultSet.getLong("user_id"));
            booksIdList.add(onlineOrderBook);
        } while (resultSet.next());
        return booksIdList;
    }
}
