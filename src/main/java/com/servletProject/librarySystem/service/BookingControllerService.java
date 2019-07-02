package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.domen.BookCatalog;
import com.servletProject.librarySystem.domen.CompletedOrders;
import com.servletProject.librarySystem.domen.CopiesOfBooks;
import com.servletProject.librarySystem.domen.OnlineOrderBook;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.OnlineOrderModel;
import com.servletProject.librarySystem.exception.ThereAreNoBooksFoundException;
import com.servletProject.librarySystem.service.data.BookCatalogService;
import com.servletProject.librarySystem.service.data.CopiesOfBooksService;
import com.servletProject.librarySystem.service.data.OnlineOrderBookService;
import com.servletProject.librarySystem.service.data.UserService;
import com.servletProject.librarySystem.utils.CreateEntityUtil;
import com.servletProject.librarySystem.utils.OrdersUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingControllerService {

    private final CopiesOfBooksService copiesOfBooksService;
    private final OnlineOrderBookService orderBookService;
    private final BookCatalogService bookCatalogService;
    private final UserService userService;

    public BookingControllerService(CopiesOfBooksService copiesOfBooksService,
                                    OnlineOrderBookService orderBookService,
                                    BookCatalogService bookCatalogService,
                                    UserService userService
    ) {
        this.copiesOfBooksService = copiesOfBooksService;
        this.orderBookService = orderBookService;
        this.bookCatalogService = bookCatalogService;
        this.userService = userService;
    }

    @Transactional
    public void reserveBook(long copyId, long readerId) {
        if (copiesOfBooksService.ifPresent(copyId)) {
            copiesOfBooksService.updateAvailabilityOfCopy(false, copyId);
            orderBookService.reserveBookCopy(copyId, readerId);
        } else throw new ThereAreNoBooksFoundException("This book copy is not available.");
    }

    public List<OnlineOrderModel> getListOfReservedBooksByUserEmail(String email) {
        Long userId = userService.getUserIdByEmail(email);
        List<OnlineOrderBook> orderBookList = orderBookService.findAllByUserIdIn(userId);
        return prepareListOfReservedBooksByUserId(orderBookList);
    }

    public List<OnlineOrderModel> getListOfReservedBooksByUserId(long userId) {
        List<OnlineOrderBook> orderBookList = orderBookService.findAllByUserIdIn(userId);
        return prepareListOfReservedBooksByUserId(orderBookList);
    }

    public List<OnlineOrderModel> getListOfAllReservedBooks() {
        List<OnlineOrderBook> reservedBooksCopy = orderBookService.findAllOrders();
        return prepareListOfReservedBooksByUserId(reservedBooksCopy);
    }

    private List<OnlineOrderModel> prepareListOfReservedBooksByUserId(List<OnlineOrderBook> orderBookList) {

        List<Long> ordersList = OrdersUtil.getBookCopyIdFromOnlineOrderList(orderBookList);
        List<CopiesOfBooks> copyBookList = copiesOfBooksService.findAllById(ordersList);
        List<Long> bookIdList = OrdersUtil.getBookIdFromBookCopyList(copyBookList);
        List<BookCatalog> bookCatalogList = bookCatalogService.findAllById(bookIdList);

        return CreateEntityUtil
                .createOnlineOrderModelEntityList(copyBookList, bookCatalogList, orderBookList);
    }

    @Transactional
    public void cancelOrder(Long bookCopyId) {
        orderBookService.cancelOrderByReader(bookCopyId);
        copiesOfBooksService.updateAvailabilityOfCopy(true, bookCopyId);
    }
}

