package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.dao.BookingDao;
import com.servletProject.librarySystem.dao.transactionManager.TransactionManager;
import com.servletProject.librarySystem.domen.CopiesOfBooks;
import com.servletProject.librarySystem.domen.OnlineOrderBook;
import com.servletProject.librarySystem.domen.UserOrdersTransferObject;
import com.servletProject.librarySystem.utils.BookingUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private BookingDao bookingDao = new BookingDao();

    public void reserveABook(long copyId, long readerId) throws SQLException {
        try {
            TransactionManager.beginTransaction();
            CopiesOfBooks bookCopyByCopyId = bookingDao.findBookCopyByCopyIdAndAvailability(copyId);
            if (bookCopyByCopyId != null) {
                bookingDao.updateAvailabilityOfCopy(false, copyId);
                bookingDao.reserveBookCopy(copyId, readerId);
            } else throw new SQLException();
        } catch (SQLException e) {
            TransactionManager.rollBackTransaction();
            throw e;
        } finally {
            TransactionManager.commitTransaction();
        }
    }

    public List<UserOrdersTransferObject> getListOfReservedBooksByUser(long userId) throws SQLException {
        try {
            TransactionManager.beginTransaction();
            Long[] allBooksCopyByReaderId = bookingDao.findAllReservedBooksCopyByReaderId(userId);
            List<UserOrdersTransferObject> reservedBooks = new ArrayList<>();
            if (allBooksCopyByReaderId.length > 0) {
                BookingUtil.getReaderOrdersByReaderId(reservedBooks, allBooksCopyByReaderId, bookingDao, userId);
            }
            return reservedBooks;
        } catch (SQLException e) {
            TransactionManager.rollBackTransaction();
            throw e;
        } finally {
            TransactionManager.commitTransaction();
        }
    }

    public List<UserOrdersTransferObject> getListOfAllReservedBooks() throws SQLException {
        try {
            TransactionManager.beginTransaction();
            List<OnlineOrderBook> reservedBooksCopy = bookingDao.findAllReservedBooksCopy();
            List<UserOrdersTransferObject> reservedBooks = new ArrayList<>();
            if (!reservedBooksCopy.isEmpty()) {
                BookingUtil.getReaderOrdersByReaderId(reservedBooks, reservedBooksCopy, bookingDao);
            }
            return reservedBooks;
        } catch (SQLException e) {
            TransactionManager.rollBackTransaction();
            throw e;
        } finally {
            TransactionManager.commitTransaction();
        }
    }
}

