package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.dao.BookingDao;
import com.servletProject.librarySystem.dao.transactionManager.TransactionManager;
import com.servletProject.librarySystem.domen.CopiesOfBooks;
import com.servletProject.librarySystem.domen.OnlineOrderBook;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.OnlineOrderBookModel;
import com.servletProject.librarySystem.utils.BookingUtil;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
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

    public List<OnlineOrderBookModel> getListOfReservedBooksByUser(long userId) throws SQLException {
        try {
            TransactionManager.beginTransaction();
            Long[] allBooksCopyByReaderId = bookingDao.findAllReservedBooksCopyByReaderId(userId);
            List<OnlineOrderBookModel> reservedBooks = new ArrayList<>();
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

    public List<OnlineOrderBookModel> getListOfAllReservedBooks() throws SQLException {
        try {
            TransactionManager.beginTransaction();
            List<OnlineOrderBook> reservedBooksCopy = bookingDao.findAllReservedBooksCopy();
            List<OnlineOrderBookModel> reservedBooks = new ArrayList<>();
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

