package com.servletProject.librarySystem.dao;

import com.servletProject.librarySystem.dao.queries.BookOrderDaoQueries;
import com.servletProject.librarySystem.dao.transactionManager.TransactionManager;
import com.servletProject.librarySystem.dao.transactionManager.WrapConnection;
import com.servletProject.librarySystem.domen.BookCatalog;
import com.servletProject.librarySystem.domen.CopiesOfBooks;
import com.servletProject.librarySystem.domen.OnlineOrderBook;
import com.servletProject.librarySystem.utils.DaoUtil;
import com.servletProject.librarySystem.utils.DomainModelUtil;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookingDao {

    public void updateAvailabilityOfCopy(boolean availability, long id) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(BookOrderDaoQueries.UPDATE_AVAILABILITY_OF_BOOK_COPY);
            preparedStatement.setBoolean(1, availability);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        }
    }

    public CopiesOfBooks findBookCopyByCopyIdAndAvailability(long copyId) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(BookOrderDaoQueries.FIND_BOOK_COPY_BY_ID_AND_AVAILABILITY);
            preparedStatement.setLong(1, copyId);
            preparedStatement.setBoolean(2, true);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Map<String, String> copyBookParameterMap = DomainModelUtil.createCopyBookParameterMap(resultSet);
                return DomainModelUtil.createBookCopyFromMap(copyBookParameterMap);
            } else throw new SQLException();
        }
    }

    public void reserveBookCopy(long copyId, long readerId) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(BookOrderDaoQueries.RESERVE_BOOK_COPY);

            long id = DaoUtil.getNextUserId(BookOrderDaoQueries.GET_NEXT_ORDER_ID);
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, readerId);
            preparedStatement.setLong(3, copyId);
            preparedStatement.executeUpdate();
        }
    }

    public Long[] findAllReservedBooksCopyByReaderId(long readerId) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(BookOrderDaoQueries.FIND_ALL_RESERVED_BOOKS_COPY_BY_READER_ID);
            preparedStatement.setLong(1, readerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return DaoUtil.getAllBooksId(resultSet, "book_copy_id");
            } else return new Long[0];
        }
    }
    public Long[] findAllOrderedBookFromCatalog(Long[] copyIdList) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(BookOrderDaoQueries.FIND_ALL_BOOKS_ID_BY_COPY_ID);
            final Array sqlArray = connection.createArrayOf("int8", copyIdList);
            preparedStatement.setArray(1, sqlArray);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return DaoUtil.getAllBooksId(resultSet, "id_book");
            } else throw new SQLException();
        }
    }

    public List<BookCatalog> findAllBooksParametersIn(Long bookId) throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(BookOrderDaoQueries.FIND_BOOK_BY_ID);
            preparedStatement.setLong(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                List<Map<String, String>> booksMaps = DaoUtil.getBooksMaps(resultSet);
                assert booksMaps != null;
                return booksMaps.stream().map(DomainModelUtil::createBookFromMap).collect(Collectors.toList());
            } else throw new SQLException();
        }
    }

    public List<OnlineOrderBook> findAllReservedBooksCopy() throws SQLException {
        try (WrapConnection connection = TransactionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(BookOrderDaoQueries.FIND_ALL_RESERVED_BOOKS_COPY);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return DaoUtil.getAllOrders(resultSet);
            } else throw new SQLException();
        }
    }
}