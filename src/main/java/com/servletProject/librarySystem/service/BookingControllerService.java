package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.domen.*;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.OnlineOrderModel;
import com.servletProject.librarySystem.exception.PermissionToActionIsAbsentException;
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
    public void reserveBook(long copyId, String nickName) {
        if (copiesOfBooksService.ifPresent(copyId)) {
            copiesOfBooksService.updateAvailabilityOfCopy(false, copyId);
            UserEntity user = userService.getUserByNickName(nickName);
            orderBookService.reserveBookCopy(copyId, user.getId());
        } else throw new ThereAreNoBooksFoundException("This book copy is not available.");
    }

    public List<OnlineOrderModel> getListOfReservedBooksByUserEmail(String librarian, String email) {
        UserEntity user = userService.getUserByNickName(librarian);
        if (user.getRoles().contains(Role.LIBRARIAN)) {
            Long userId = userService.getUserIdByEmail(email);
            List<OnlineOrderBook> orderBookList = orderBookService.findAllByUserId(userId);
            return prepareListOfReservedBooksByUserId(orderBookList);
        } else throw new PermissionToActionIsAbsentException("You do not have permission to this functionality.");
    }

    public List<OnlineOrderModel> getListOfReservedBooksByUserId(String nickName) {
        UserEntity user = userService.getUserByNickName(nickName);
        List<OnlineOrderBook> orderBookList = orderBookService.findAllByUserId(user.getId());
        return prepareListOfReservedBooksByUserId(orderBookList);
    }

    public List<OnlineOrderModel> getListOfAllReservedBooks(String librarian) {
        UserEntity user = userService.getUserByNickName(librarian);
        if (user.getRoles().contains(Role.LIBRARIAN)) {
            List<OnlineOrderBook> reservedBooksCopy = orderBookService.findAllOrders();
            return prepareListOfReservedBooksByUserId(reservedBooksCopy);
        } else throw new PermissionToActionIsAbsentException("You do not have permission to this functionality.");
    }

    private List<OnlineOrderModel> prepareListOfReservedBooksByUserId(List<OnlineOrderBook> orderBookList) {

        List<Long> ordersList = OrdersUtil.getBookCopyIdFromOnlineOrderList(orderBookList);
        List<CopiesOfBooks> copyBookList = copiesOfBooksService.findAllById(ordersList);
        List<Long> bookIdList = OrdersUtil.getBookIdFromBookCopyList(copyBookList);
        List<BookCatalog> bookCatalogList = bookCatalogService.findAllByIdIn(bookIdList);

        return CreateEntityUtil
                .createOnlineOrderModelEntityList(copyBookList, bookCatalogList, orderBookList);
    }

    @Transactional
    public void cancelOrder(Long bookCopyId, UserEntity user) {
        orderBookService.cancelOrderByReader(bookCopyId, user);
        copiesOfBooksService.updateAvailabilityOfCopy(true, bookCopyId);
    }
}

