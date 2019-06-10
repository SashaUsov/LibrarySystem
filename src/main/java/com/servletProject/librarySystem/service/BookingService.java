package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.dao.BookingDao;
import com.servletProject.librarySystem.dao.BooksDao;
import com.servletProject.librarySystem.dao.transactionManager.TransactionManager;
import com.servletProject.librarySystem.domen.CopiesOfBooks;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class BookingService {
    private BooksDao booksDao = new BooksDao();
    private BookingDao bookingDao = new BookingDao();

    public void reserveABook(long copyId) throws SQLException {
        try {
            TransactionManager.beginTransaction();
            final CopiesOfBooks bookCopyByCopyId = bookingDao.findAvailableBookCopyByCopyId(copyId);
            if (bookCopyByCopyId != null) {

            }
        } catch (SQLException e) {
            TransactionManager.rollBackTransaction();
            throw e;
        } finally {
            TransactionManager.commitTransaction();
        }
    }
}
