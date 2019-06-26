package com.servletProject.librarySystem.dao;

import com.servletProject.librarySystem.dao.queries.BookDaoQueries;
import com.servletProject.librarySystem.dao.transactionManager.TransactionManager;
import com.servletProject.librarySystem.dao.transactionManager.WrapConnection;
import com.servletProject.librarySystem.domen.CopiesOfBooks;
import com.servletProject.librarySystem.utils.DaoUtil;
import com.servletProject.librarySystem.utils.DomainModelUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BooksDao {

//    public Long findBookIdByBookTitle(String bookTitle) throws SQLException {
//        try (WrapConnection connection = TransactionManager.getConnection()) {
//            PreparedStatement preparedStatement = connection.prepareStatement(BookDaoQueries.FIND_BOOK_ID_BY_BOOK_TITLE);
//            preparedStatement.setString(1, bookTitle);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                long id = resultSet.getLong("id");
//                return id;
//            } else {
//                return null;
//            }
//        }
//    }

    public void saveBook(Map<String, String> paramMap) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(BookDaoQueries.SAVE_BOOK);
            long id = DaoUtil.getNextUserId(BookDaoQueries.GET_NEXT_BOOK_ID);

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
            PreparedStatement preparedStatement = connection.prepareStatement(BookDaoQueries.SAVE_BOOK_COPY);
            long id = DaoUtil.getNextUserId(BookDaoQueries.GET_NEXT_BOOK_COPY_ID);
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, bookId);
            preparedStatement.setString(3, "good");
            preparedStatement.executeUpdate();
        }
        if (incrementTotalAmount) {
            incrementBookTotalAmount(bookId);
        }
    }

//    public List<Map<String, String>> getAllBook() throws SQLException {
//        try (WrapConnection connection = TransactionManager.getConnection()) {
//            PreparedStatement preparedStatement = connection.prepareStatement(BookDaoQueries.FIND_ALL_BOOK_FROM_CATALOG);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            List<Map<String, String>> bookCatalog = new ArrayList<>();
//            if (resultSet.next()) {
//                bookCatalog = DaoUtil.getBooksMaps(resultSet);
//            }
//            return bookCatalog;
//        }
//    }

//    public List<Map<String, String>> findBookByTitle(String title) throws SQLException {
//        return findBookBy(BookDaoQueries.FIND_ALL_BOOK_BY_BOOK_TITLE, title);
//    }
//
//    public List<Map<String, String>> findBookByAuthor(String author) throws SQLException {
//        return findBookBy(BookDaoQueries.FIND_ALL_BOOK_BY_BOOK_AUTHOR, author);
//    }
//
//    public List<Map<String, String>> findBookByGenre(String genre) throws SQLException {
//        return findBookBy(BookDaoQueries.FIND_ALL_BOOK_BY_BOOK_GENRE, genre);
//    }

//    public List<Map<String, String>> getAllBookCopy(long id) throws SQLException {
//        try (WrapConnection connection = TransactionManager.getConnection()) {
//            PreparedStatement preparedStatement = connection.prepareStatement(BookDaoQueries.FIND_ALL_BOOK_COPY);
//            preparedStatement.setLong(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            List<Map<String, String>> bookCopy = DaoUtil.createBooksCopy(resultSet);
//            if (bookCopy != null && !bookCopy.isEmpty()) {
//                return bookCopy;
//            } else return null;
//        }
//    }

//    public List<CopiesOfBooks> findBooksInUnusableCondition() throws SQLException {
//        try (WrapConnection connection = TransactionManager.getConnection()) {
//            PreparedStatement preparedStatement = connection.prepareStatement(BookDaoQueries
//                                                                                      .FIND_ALL_BOOK_COPY_BY_AVAILABILITY_AND_CONDITION);
//            preparedStatement.setBoolean(1, true);
//            preparedStatement.setString(2, "unusable");
//            ResultSet resultSet = preparedStatement.executeQuery();
//            return getCopiesOfBooksCatalog(resultSet);
//        }
//    }

    public void deleteCopyById(long id) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(BookDaoQueries
                                                                                      .DELETE_BOOK_COPY_BY_ID_AND_AVAILABILITY);
            preparedStatement.setLong(1, id);
            preparedStatement.setBoolean(2, true);
            preparedStatement.executeUpdate();
        }
    }

//    public void decrementBookTotalAmount(long bookId) throws SQLException {
//
//        try (WrapConnection connection = TransactionManager.getConnection()) {
//            PreparedStatement preparedStatement = connection.prepareStatement(BookDaoQueries.UPDATE_DECREMENT_BOOK_TOTAL_AMOUNT);
//
//            preparedStatement.setLong(1, bookId);
//            preparedStatement.executeUpdate();
//        }
//    }

    public void deleteFromArchive(long bookId) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(BookDaoQueries
                                                                                      .DELETE_FROM_ARCHIVE_BY_USER_ID_AND_COPY_ID);
            preparedStatement.setLong(1, bookId);
            preparedStatement.executeUpdate();
        }
    }

//    public Long findBookIdByBookCopyId(long copyId) throws SQLException {
//        try (WrapConnection connection = TransactionManager.getConnection()) {
//            PreparedStatement preparedStatement = connection.prepareStatement(BookDaoQueries.FIND_BOOK_ID_BY_BOOK_COPY_ID);
//            preparedStatement.setLong(1, copyId);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                long id = resultSet.getLong("id_book");
//                return id;
//            } else {
//                return null;
//            }
//        }
//    }

    private List<CopiesOfBooks> getCopiesOfBooksCatalog(ResultSet resultSet) throws SQLException {
        List<Map<String, String>> bookCopy = DaoUtil.createBooksCopy(resultSet);
        if (bookCopy != null && !bookCopy.isEmpty()) {
            List<CopiesOfBooks> copiesOfBooksList = new ArrayList<>();
            return DomainModelUtil.createCopyBookCatalog(copiesOfBooksList, bookCopy);
        } else return null;
    }

//    private void incrementBookTotalAmount(long bookId) throws SQLException {
//        try (WrapConnection connection = TransactionManager.getConnection()) {
//            PreparedStatement preparedStatement = connection.prepareStatement(BookDaoQueries.UPDATE_INCREMENT_BOOK_TOTAL_AMOUNT);
//
//            preparedStatement.setLong(1, bookId);
//            preparedStatement.executeUpdate();
//        }
//    }

    private List<Map<String, String>> findBookBy(String sql, String text) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, text);
            final ResultSet resultSet = preparedStatement.executeQuery();
            List<Map<String, String>> bookCatalog = DaoUtil.getBooksMaps(resultSet);
            return bookCatalog;
        }
    }
}