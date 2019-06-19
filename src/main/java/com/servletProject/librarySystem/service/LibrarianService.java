package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.dao.BookingDao;
import com.servletProject.librarySystem.dao.LibrarianDao;
import com.servletProject.librarySystem.dao.UserDao;
import com.servletProject.librarySystem.dao.transactionManager.TransactionManager;
import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.domen.UserOrdersTransferObject;
import com.servletProject.librarySystem.utils.BookingUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibrarianService {
    private final UserDao userDao = new UserDao();
    private final BookingDao bookingDao = new BookingDao();
    private final LibrarianDao librarianDao = new LibrarianDao();

    public List<UserOrdersTransferObject> getAllReservedBooksByUser(String email) throws SQLException {
        UserEntity user = userDao.findUserByEmail(email);
        List<UserOrdersTransferObject> reservedBooks = new ArrayList<>();
        if (user != null) {
            long userId = user.getId();
            Long[] allBooksCopyByReaderId = bookingDao.findAllReservedBooksCopyByReaderId(userId);
            if (allBooksCopyByReaderId.length > 0) {
                BookingUtil.getReaderOrdersByReaderId(reservedBooks, allBooksCopyByReaderId, bookingDao, userId);
            }
        }
        return reservedBooks;
    }

    public void giveBookToTheReader(Long bookCopyId, Long userId, Long librarianId) {

    }

    public void cancelOrder(Long bookCopyId) throws SQLException {
        try {
            TransactionManager.beginTransaction();
            librarianDao.deleteFromOrderTable(bookCopyId);
            bookingDao.updateAvailabilityOfCopy(true, bookCopyId);
        } catch (SQLException | NullPointerException e) {
            TransactionManager.rollBackTransaction();
            throw e;
        } finally {
            TransactionManager.commitTransaction();
        }
    }
}
