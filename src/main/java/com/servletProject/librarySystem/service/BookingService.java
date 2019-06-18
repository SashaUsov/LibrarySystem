package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.dao.BookingDao;
import com.servletProject.librarySystem.dao.transactionManager.TransactionManager;
import com.servletProject.librarySystem.domen.BookCatalog;
import com.servletProject.librarySystem.domen.CopiesOfBooks;
import com.servletProject.librarySystem.domen.UserOrdersTransferObject;

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

    public List<UserOrdersTransferObject> getListOfReservedBooks(long userId) throws SQLException {
        try {
            TransactionManager.beginTransaction();
            List<UserOrdersTransferObject> reservedBooks = new ArrayList<>();
            Long[] allBooksCopyByReaderId = bookingDao.findAllBooksCopyByReaderId(userId);
            if (allBooksCopyByReaderId.length > 0) {
                getReaderOrders(reservedBooks, allBooksCopyByReaderId);
            }
            return reservedBooks;
        } catch (SQLException e) {
            TransactionManager.rollBackTransaction();
            throw e;
        } finally {
            TransactionManager.commitTransaction();
        }
    }

    private void getReaderOrders(List<UserOrdersTransferObject> reservedBooks, Long[] allBooksCopyByReaderId) throws SQLException {
        Long[] allOrderedBooksFromCatalog = bookingDao.findAllOrderedBookFromCatalog(allBooksCopyByReaderId);
        for (int i = 0; i <= allBooksCopyByReaderId.length; i++) {
            createOrdersTransferObject(reservedBooks, allBooksCopyByReaderId[i], allOrderedBooksFromCatalog[i]);
        }
    }

    private void createOrdersTransferObject(List<UserOrdersTransferObject> reservedBooks, Long uniqueId, Long bookId) throws SQLException {
        List<BookCatalog> book = bookingDao.findAllBooksParametersIn(bookId);
        BookCatalog bookCatalog = book.get(0);
        UserOrdersTransferObject uOTO = new UserOrdersTransferObject();
        uOTO.setUniqueId(uniqueId);
        uOTO.setBookTitle(bookCatalog.getBookTitle());
        uOTO.setBookAuthor(bookCatalog.getBookAuthor());
        uOTO.setGenre(bookCatalog.getGenre());
        uOTO.setYearOfPublication(bookCatalog.getYearOfPublication());

        reservedBooks.add(uOTO);
    }
}

