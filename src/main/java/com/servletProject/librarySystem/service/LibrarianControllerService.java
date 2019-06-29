package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.dao.transactionManager.TransactionManager;
import com.servletProject.librarySystem.domen.*;
import com.servletProject.librarySystem.domen.dto.archiveBookUsage.ArchiveBookModel;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.OnlineOrderModel;
import com.servletProject.librarySystem.service.data.*;
import com.servletProject.librarySystem.utils.BookingUtil;
import com.servletProject.librarySystem.utils.CreateEntityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LibrarianControllerService {
    private final UserService userService;
    private final CopiesOfBooksService copiesOfBooksService;
    private final OnlineOrderBookService orderBookService;
    private final BookCatalogService bookCatalogService;
    private final CompletedOrdersService completedOrdersService;

    public LibrarianControllerService(UserService userService, CopiesOfBooksService copiesOfBooksService,
                                      OnlineOrderBookService orderBookService, BookCatalogService bookCatalogService,
                                      CompletedOrdersService completedOrdersService) {
        this.userService = userService;
        this.copiesOfBooksService = copiesOfBooksService;
        this.orderBookService = orderBookService;
        this.bookCatalogService = bookCatalogService;
        this.completedOrdersService = completedOrdersService;
    }

/*    Extract in BookingControllerService
    public List<OnlineOrderModel> getAllReservedBooksByUser(String email) {
        Long userId = userService.getUserIdByEmail(email);
        return ordersUtil.getListOfReservedBooksByUserId(userId);

    }*/

    @Transactional
    public void giveBookToTheReader(Long bookCopyId, Long userId, Long librarianId) {
        orderBookService.deleteOrderByCopyIdAndUserId(bookCopyId, userId);
        completedOrdersService.saveOrder(userId, librarianId, bookCopyId);
    }

    @Transactional
    public void cancelOrder(Long bookCopyId) {
            orderBookService.cancelOrder(bookCopyId);
            copiesOfBooksService.updateAvailabilityOfCopy(true, bookCopyId);
    }

    public List<OnlineOrderModel> getListOfCompletedOrdersByReader(String email) {
            Long idUser = userService.getUserIdByEmail(email);
        List<CompletedOrders> completedOrders = completedOrdersService.findAllByUserId(idUser);
        return prepareListOfCompletedOrders(completedOrders);
    }

    public List<OnlineOrderModel> getListOfAllCompletedOrders() {
        List<CompletedOrders> completedOrders = completedOrdersService.findAllCompletedOrders();
        return prepareListOfCompletedOrders(completedOrders);
    }

    private List<OnlineOrderModel> prepareListOfCompletedOrders(List<CompletedOrders> completedOrders) {
        List<Long> ordersList = getBookCopyIdFromCompletedOrdersList(completedOrders);
        List<CopiesOfBooks> copyBookList = copiesOfBooksService.findAllById(ordersList);
        List<Long> bookIdList = getBookIdFromBookCopyList(copyBookList);
        List<BookCatalog> bookCatalogList = bookCatalogService.findAllById(bookIdList);

        return CreateEntityUtil
                .createCompletedOrdersEntityList(copyBookList, bookCatalogList, completedOrders);
    }

    private List<Long> getBookIdFromBookCopyList(List<CopiesOfBooks> copyBookList) {
        return copyBookList.stream()
                .map(CopiesOfBooks::getIdBook)
                .collect(Collectors.toList());
    }

    private List<Long> getBookCopyIdFromOnlineOrderList(List<OnlineOrderBook> orderBookList) {
        return orderBookList.stream()
                .map(OnlineOrderBook::getIdBookCopy)
                .collect(Collectors.toList());
    }

    private List<Long> getBookCopyIdFromCompletedOrdersList(List<CompletedOrders> orderBookList) {
        return orderBookList.stream()
                .map(CompletedOrders::getIdBook)
                .collect(Collectors.toList());
    }

    public void returnBookToTheCatalog(Long readerId, Long copyId, String condition) throws SQLException {
        try {
            TransactionManager.beginTransaction();
            librarianDao.putBookInUsageArchive(copyId, readerId, condition);
            librarianDao.deleteFromCompletedOrdersByCopyId(copyId);
            updateCopyOfBookInfo(copyId, condition);
        } catch (SQLException | NullPointerException e) {
            TransactionManager.rollBackTransaction();
            throw e;
        } finally {
            TransactionManager.commitTransaction();
        }
    }

    public List<ArchiveBookModel> getListOfActiveUsageByUser(String email) throws SQLException {
        try {
            TransactionManager.beginTransaction();
            UserEntity user = userDao.findUserByEmail(email);
            List<ArchiveBookModel> userArchive = new ArrayList<>();
            if (user != null) {
                createArchiveBookUsage(user, userArchive);
            }
            return userArchive;
        } catch (SQLException | NullPointerException e) {
            TransactionManager.rollBackTransaction();
            throw e;
        } finally {
            TransactionManager.commitTransaction();
        }
    }

    public List<ArchiveBookModel> getListOfAllArchiveUsage() throws SQLException {
        try {
            TransactionManager.beginTransaction();
            List<ArchiveBookModel> userArchive = new ArrayList<>();
            List<ArchiveBookUsage> booksArchive = librarianDao.findAllUsageBooksArchive();
            if (booksArchive != null && !booksArchive.isEmpty()) {
                createFullArchive(userArchive, booksArchive);
            }
            return userArchive;
        } catch (SQLException | NullPointerException e) {
            TransactionManager.rollBackTransaction();
            throw e;
        } finally {
            TransactionManager.commitTransaction();
        }
    }

    public List<CopiesOfBooks> unusableConditionBooksList() throws SQLException {
        try {
            TransactionManager.beginTransaction();
            List<CopiesOfBooks> unusableBooks = booksDao.findBooksInUnusableCondition();
            if (unusableBooks != null && !unusableBooks.isEmpty()) {
                return unusableBooks;
            } else throw new SQLException();
        } catch (SQLException e) {
            TransactionManager.rollBackTransaction();
            throw e;
        } finally {
            TransactionManager.commitTransaction();
        }
    }

    private void createFullArchive(List<ArchiveBookModel> userArchive, List<ArchiveBookUsage> booksArchive) throws SQLException {
        Long[] copyIdList = booksArchive.stream().map(ArchiveBookUsage::getIdCopiesBook).toArray(Long[]::new);
        Long[] allOrderedBookFromCatalog = bookingDao.findAllOrderedBookFromCatalog(copyIdList);
        if (allOrderedBookFromCatalog != null && allOrderedBookFromCatalog.length > 0) {
            findFullArchiveParameters(userArchive, booksArchive, copyIdList, allOrderedBookFromCatalog);
        }
    }

    private void findFullArchiveParameters(List<ArchiveBookModel> userArchive, List<ArchiveBookUsage> booksArchive,
                                           Long[] copyIdList, Long[] allOrderedBookFromCatalog) throws SQLException {
        Map<Long, Long> collect = booksArchive.stream()
                .collect(Collectors.toMap(ArchiveBookUsage::getIdCopiesBook, ArchiveBookUsage::getIdReader));
        for (int i = 0; i < allOrderedBookFromCatalog.length; i++) {
            fillFullListOfArchiveUsage(userArchive, copyIdList[i], allOrderedBookFromCatalog[i], collect.get(copyIdList[i]));
        }
    }

    private void fillFullListOfArchiveUsage(List<ArchiveBookModel> userArchive, Long key,
                                            Long bookId, Long userId) throws SQLException {
        String name = userDao.findFullUserName(userId);
        BookingUtil.createArchiveBookTransferObject(userArchive, key, bookId,
                                                    bookingDao, userId, name);
    }

    private void createArchiveBookUsage(UserEntity user, List<ArchiveBookModel> userArchive) throws SQLException {
        long userId = user.getId();
        String name = user.getFirstName() + " " + user.getLastName();
        List<ArchiveBookUsage> booksArchive = librarianDao.findAllUsageBooksByUserId(userId);
        if (booksArchive != null && !booksArchive.isEmpty()) {
            findAllArchiveParameters(userArchive, booksArchive, name);
        }
    }

    private void findAllArchiveParameters(List<ArchiveBookModel> userArchive,
                                          List<ArchiveBookUsage> booksArchive,
                                          String name) throws SQLException {
        Long[] copyIdList = booksArchive.stream().map(ArchiveBookUsage::getIdCopiesBook).toArray(Long[]::new);
        Long[] allOrderedBookFromCatalog = bookingDao.findAllOrderedBookFromCatalog(copyIdList);
        if (allOrderedBookFromCatalog != null && allOrderedBookFromCatalog.length > 0) {
            fillTheListOfArchiveUsage(userArchive, booksArchive, copyIdList, allOrderedBookFromCatalog, name);
        }
    }

    private void fillTheListOfArchiveUsage(List<ArchiveBookModel> userArchive,
                                           List<ArchiveBookUsage> booksArchive, Long[] copyIdList,
                                           Long[] allOrderedBookFromCatalog, String name)
            throws SQLException {
        Map<Long, Long> collect = booksArchive.stream()
                .collect(Collectors.toMap(ArchiveBookUsage::getIdCopiesBook, ArchiveBookUsage::getIdReader));
        for (int i = 0; i < allOrderedBookFromCatalog.length; i++) {
            BookingUtil.createArchiveBookTransferObject(userArchive, copyIdList[i], allOrderedBookFromCatalog[i],
                                                        bookingDao, collect.get(copyIdList[i]), name);
        }
    }

    private void findAllWaitingReservedCopyByUser(UserEntity user, List<OnlineOrderModel> reservedBooks) throws SQLException {
        long userId = user.getId();
        Long[] allBooksCopyByReaderId = bookingDao.findAllReservedBooksCopyByReaderId(userId);
        if (allBooksCopyByReaderId.length > 0) {
            BookingUtil.getReaderOrdersByReaderId(reservedBooks, allBooksCopyByReaderId, bookingDao, userId);
        }
    }

    private void createCompletedOrdersList(List<OnlineOrderModel> reservedBooks, long userId) throws SQLException {
        List<CompletedOrders> completedOrders = librarianDao.findAllCompletedOrdersCopyIdByUserId(userId);
        if (completedOrders != null && !completedOrders.isEmpty()) {
            findAllOrderParameters(reservedBooks, completedOrders);
        }
    }

    private void findAllOrderParameters(List<OnlineOrderModel> reservedBooks, List<CompletedOrders> completedOrders) throws SQLException {
        Long[] copyIdList = completedOrders.stream().map(CompletedOrders::getIdBook).toArray(Long[]::new);
        Long[] allOrderedBookFromCatalog = bookingDao.findAllOrderedBookFromCatalog(copyIdList);
        if (allOrderedBookFromCatalog != null && allOrderedBookFromCatalog.length > 0) {
            fillTheListOfCompletedOrders(reservedBooks, completedOrders, copyIdList, allOrderedBookFromCatalog);
        }
    }

    private void fillTheListOfCompletedOrders(List<OnlineOrderModel> reservedBooks, List<CompletedOrders> completedOrders, Long[] copyIdList, Long[] allOrderedBookFromCatalog) throws SQLException {
        Map<Long, Long> collect = completedOrders.stream()
                .collect(Collectors.toMap(CompletedOrders::getIdBook, CompletedOrders::getIdReader));
        for (int i = 0; i < allOrderedBookFromCatalog.length; i++) {
            BookingUtil.createOrdersTransferObject(reservedBooks, copyIdList[i], allOrderedBookFromCatalog[i],
                                                   bookingDao, collect.get(copyIdList[i]));
        }
    }

    private void updateCopyOfBookInfo(Long copyId, String condition) throws SQLException {
        boolean availability = isAvailability(condition);
        librarianDao.updateAvailabilityAndConditionOfCopy(copyId, condition, availability);
    }

    private boolean isAvailability(String condition) {
        boolean b = true;
        switch (condition) {
            case "good":
                b = true;
                break;
            case "bad":
                b = true;
                break;
            case "unusable":
                b = false;
                break;
        }
        return b;
    }
}