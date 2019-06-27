package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.domen.BookCatalog;
import com.servletProject.librarySystem.domen.CopiesOfBooks;
import com.servletProject.librarySystem.domen.OnlineOrderBook;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.OnlineOrderModel;
import com.servletProject.librarySystem.exception.ThereAreNoBooksFoundException;
import com.servletProject.librarySystem.service.data.BookCatalogService;
import com.servletProject.librarySystem.service.data.CopiesOfBooksService;
import com.servletProject.librarySystem.service.data.OnlineOrderBookService;
import com.servletProject.librarySystem.utils.CreateEntityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingControllerService {

    private final CopiesOfBooksService copiesOfBooksService;
    private final OnlineOrderBookService orderBookService;
    private final BookCatalogService bookCatalogService;

    public BookingControllerService(CopiesOfBooksService copiesOfBooksService,
                                    OnlineOrderBookService orderBookService,
                                    BookCatalogService bookCatalogService) {
        this.copiesOfBooksService = copiesOfBooksService;
        this.orderBookService = orderBookService;
        this.bookCatalogService = bookCatalogService;
    }

    @Transactional
    public void reserveBook(long copyId, long readerId) {
        if (copiesOfBooksService.ifPresent(copyId)) {
            copiesOfBooksService.updateAvailabilityOfCopy(false, copyId);
            orderBookService.reserveBookCopy(copyId, readerId);
        } else throw new ThereAreNoBooksFoundException("This book copy is not available.");
    }

    public List<OnlineOrderModel> getListOfReservedBooksByUser(long userId) {
        List<OnlineOrderBook> orderBookList = orderBookService.findAllByUserIdIn(userId);
        List<Long> ordersList = getBookCopyIdFromOnlineOrderList(orderBookList);
        List<CopiesOfBooks> copyBookList = copiesOfBooksService.findAllById(ordersList);
        List<Long> bookIdList = getBookIdFromBookCopyList(copyBookList);
        List<BookCatalog> bookCatalogList = bookCatalogService.findAllById(bookIdList);

        return CreateEntityUtil
                .createOnlineOrderModelEntityList(copyBookList, bookCatalogList, orderBookList);
    }

    public List<OnlineOrderModel> getListOfAllReservedBooks() throws SQLException {

        List<OnlineOrderBook> reservedBooksCopy = orderBookService.findAllOrders();
        List<Long> ordersList = getBookCopyIdFromOnlineOrderList(reservedBooksCopy);
        List<CopiesOfBooks> copyBookList = copiesOfBooksService.findAllById(ordersList);
        List<Long> bookIdList = getBookIdFromBookCopyList(copyBookList);
        List<BookCatalog> bookCatalogList = bookCatalogService.findAllById(bookIdList);

        return CreateEntityUtil.createOnlineOrderModelEntityList(copyBookList, bookCatalogList, reservedBooksCopy);

    }

    private List<Long> getBookCopyIdFromOnlineOrderList(List<OnlineOrderBook> orderBookList) {
        return orderBookList.stream().map(OnlineOrderBook::getIdBookCopy).collect(Collectors.toList());
    }

    private List<Long> getBookIdFromBookCopyList(List<CopiesOfBooks> copyBookList) {
        return copyBookList.stream().map(CopiesOfBooks::getIdBook).collect(Collectors.toList());
    }
}

