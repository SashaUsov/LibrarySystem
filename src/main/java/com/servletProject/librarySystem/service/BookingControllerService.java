package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.domen.OnlineOrderBook;
import com.servletProject.librarySystem.domen.Role;
import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.OnlineOrderModel;
import com.servletProject.librarySystem.exception.PermissionToActionIsAbsentException;
import com.servletProject.librarySystem.exception.ThereAreNoBooksFoundException;
import com.servletProject.librarySystem.service.data.BookCatalogService;
import com.servletProject.librarySystem.service.data.CopiesOfBooksService;
import com.servletProject.librarySystem.service.data.OnlineOrderBookService;
import com.servletProject.librarySystem.service.data.UserService;
import com.servletProject.librarySystem.utils.CreateEntityUtil;
import com.servletProject.librarySystem.utils.OrdersUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
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
            var user = userService.getUserByNickName(nickName);
            orderBookService.reserveBookCopy(copyId, user.getId());
            log.info("New book order created successfully");
        } else throw new ThereAreNoBooksFoundException("This book copy is not available.");
    }

    public List<OnlineOrderModel> getListOfReservedBooksByUserEmail(String librarian, String email) {
        var user = userService.getUserByNickName(librarian);
        if (user.getRoles().contains(Role.LIBRARIAN)) {
            var userId = userService.getUserIdByEmail(email);
            var orderBookList = orderBookService.findAllByUserId(userId);
            return prepareListOfReservedBooksByUserId(orderBookList);
        } else throw new PermissionToActionIsAbsentException("You do not have permission to this functionality.");
    }

    public List<OnlineOrderModel> getListOfReservedBooksByUserId(String nickName) {
        var user = userService.getUserByNickName(nickName);
        var orderBookList = orderBookService.findAllByUserId(user.getId());
        return prepareListOfReservedBooksByUserId(orderBookList);
    }

    public List<OnlineOrderModel> getListOfAllReservedBooks(String librarian) {
        var user = userService.getUserByNickName(librarian);
        if (user.getRoles().contains(Role.LIBRARIAN)) {
            var reservedBooksCopy = orderBookService.findAllOrders();
            return prepareListOfReservedBooksByUserId(reservedBooksCopy);
        } else throw new PermissionToActionIsAbsentException("You do not have permission to this functionality.");
    }

    private List<OnlineOrderModel> prepareListOfReservedBooksByUserId(List<OnlineOrderBook> orderBookList) {

        var ordersList = OrdersUtil.getBookCopyIdFromOnlineOrderList(orderBookList);
        var copyBookList = copiesOfBooksService.findAllById(ordersList);
        var bookIdList = OrdersUtil.getBookIdFromBookCopyList(copyBookList);
        var bookCatalogList = bookCatalogService.findAllByIdIn(bookIdList);

        return CreateEntityUtil
                .createOnlineOrderModelEntityList(copyBookList, bookCatalogList, orderBookList);
    }

    @Transactional
    public void cancelOrder(Long bookCopyId, UserEntity user) {
        orderBookService.cancelOrderByReader(bookCopyId, user);
        copiesOfBooksService.updateAvailabilityOfCopy(true, bookCopyId);
        log.info("Book order cancel successfully");
    }
}

