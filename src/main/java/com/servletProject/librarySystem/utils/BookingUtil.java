package com.servletProject.librarySystem.utils;

import com.servletProject.librarySystem.dao.BookingDao;
import com.servletProject.librarySystem.domen.BookCatalog;
import com.servletProject.librarySystem.domen.UserOrdersTransferObject;

import java.sql.SQLException;
import java.util.List;

public class BookingUtil {

    public static void getReaderOrders(List<UserOrdersTransferObject> reservedBooks, Long[] allBooksCopyByReaderId,
                                 BookingDao bookingDao, Long userId)
            throws SQLException {
        Long[] allOrderedBooksFromCatalog = bookingDao.findAllOrderedBookFromCatalog(allBooksCopyByReaderId);
        for (int i = 0; i < allBooksCopyByReaderId.length; i++) {
            BookingUtil.createOrdersTransferObject(reservedBooks, allBooksCopyByReaderId[i],
                                                   allOrderedBooksFromCatalog[i], bookingDao, userId);
        }
    }

    private static void createOrdersTransferObject(List<UserOrdersTransferObject> reservedBooks, Long uniqueId,
                                                  Long bookId, BookingDao bookingDao, Long userId)
            throws SQLException {
        List<BookCatalog> book = bookingDao.findAllBooksParametersIn(bookId);
        BookCatalog bookCatalog = book.get(0);
        UserOrdersTransferObject uOTO = new UserOrdersTransferObject();
        uOTO.setUniqueId(uniqueId);
        uOTO.setUserId(userId);
        uOTO.setBookTitle(bookCatalog.getBookTitle());
        uOTO.setBookAuthor(bookCatalog.getBookAuthor());
        uOTO.setGenre(bookCatalog.getGenre());
        uOTO.setYearOfPublication(bookCatalog.getYearOfPublication());

        reservedBooks.add(uOTO);
    }
}
