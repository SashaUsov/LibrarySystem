package com.servletProject.librarySystem.utils;

import com.servletProject.librarySystem.dao.BookingDao;
import com.servletProject.librarySystem.domen.dto.archiveBookUsage.ArchiveBookModel;
import com.servletProject.librarySystem.domen.BookCatalog;
import com.servletProject.librarySystem.domen.OnlineOrderBook;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.OnlineOrderModel;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookingUtil {

    public static void getReaderOrdersByReaderId(List<OnlineOrderModel> reservedBooks, Long[] allBooksCopyByReaderId,
                                                 BookingDao bookingDao, Long userId)
            throws SQLException {
        Long[] allOrderedBooksFromCatalog = bookingDao.findAllOrderedBookFromCatalog(allBooksCopyByReaderId);
        for (int i = 0; i < allBooksCopyByReaderId.length; i++) {
            BookingUtil.createOrdersTransferObject(reservedBooks, allBooksCopyByReaderId[i],
                                                   allOrderedBooksFromCatalog[i], bookingDao, userId);
        }
    }

    public static void createOrdersTransferObject(List<OnlineOrderModel> reservedBooks, Long uniqueId,
                                                  Long bookId, BookingDao bookingDao, Long userId)
            throws SQLException {
        List<BookCatalog> book = bookingDao.findAllBooksParametersIn(bookId);
        BookCatalog bookCatalog = book.get(0);
        OnlineOrderModel uOTO = new OnlineOrderModel();
        uOTO.setUniqueId(uniqueId);
        uOTO.setUserId(userId);
        uOTO.setBookTitle(bookCatalog.getBookTitle());
        uOTO.setBookAuthor(bookCatalog.getBookAuthor());
        uOTO.setGenre(bookCatalog.getGenre());
        uOTO.setYearOfPublication(bookCatalog.getYearOfPublication());

        reservedBooks.add(uOTO);
    }

    public static void getReaderOrdersByReaderId(List<OnlineOrderModel> reservedBooks, List<OnlineOrderBook> orderBooks,
                                                 BookingDao bookingDao)
            throws SQLException {
        Long[] copyIdArray = orderBooks.stream().map(OnlineOrderBook::getIdBookCopy).toArray(Long[]::new);
        Map<Long, Long> longMap = orderBooks.stream().collect(
                Collectors.toMap(OnlineOrderBook::getIdBookCopy, OnlineOrderBook::getIdUser));
        Long[] orderedBookFromCatalog = bookingDao.findAllOrderedBookFromCatalog(copyIdArray);
        for (int i = 0; i < copyIdArray.length; i++) {
            BookingUtil.createOrdersTransferObject(reservedBooks, copyIdArray[i],
                                                   orderedBookFromCatalog[i], bookingDao, longMap.get(copyIdArray[i]));
        }
    }

    public static void getReserveListAnswer(List<OnlineOrderModel> listOfReservedBooks, ServletRequest request,
                                            ServletResponse response, HttpSession session, String path)
            throws ServletException, IOException {
        if (listOfReservedBooks != null && !listOfReservedBooks.isEmpty()) {
            session.setAttribute("list_of_reserved_books", listOfReservedBooks);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
            requestDispatcher.forward(request, response);
        } else {
            QueryResponseUtility.sendMessage(request, response, session, "There are no pending orders.");
        }
    }

    public static void createArchiveBookTransferObject(List<ArchiveBookModel> userArchive, Long uniqueId,
                                                       Long bookId, BookingDao bookingDao,
                                                       Long userId, String name) throws SQLException {
        List<BookCatalog> book = bookingDao.findAllBooksParametersIn(bookId);
        BookCatalog bookCatalog = book.get(0);
        ArchiveBookModel uOTO = new ArchiveBookModel();
        uOTO.setUniqueId(uniqueId);
        uOTO.setUserId(userId);
        uOTO.setBookTitle(bookCatalog.getBookTitle());
        uOTO.setBookAuthor(bookCatalog.getBookAuthor());
        uOTO.setGenre(bookCatalog.getGenre());
        uOTO.setYearOfPublication(bookCatalog.getYearOfPublication());
        uOTO.setName(name);

        userArchive.add(uOTO);
    }
}