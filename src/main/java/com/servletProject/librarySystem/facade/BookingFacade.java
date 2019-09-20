package com.servletProject.librarySystem.facade;

import com.servletProject.librarySystem.domen.BookCatalog;
import com.servletProject.librarySystem.domen.CopiesOfBooks;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.OnlineOrderModel;
import com.servletProject.librarySystem.exception.DataIsNotCorrectException;
import com.servletProject.librarySystem.service.BookControllerService;
import com.servletProject.librarySystem.service.BookingControllerService;
import com.servletProject.librarySystem.service.LibrarianControllerService;
import com.servletProject.librarySystem.service.data.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
public class BookingFacade {

    private final BookingControllerService bookingControllerService;
    private final LibrarianControllerService librarianControllerService;
    private final UserService userService;
    private final BookControllerService bookControllerService;

    public BookingFacade(BookingControllerService bookingControllerService,
                         LibrarianControllerService librarianControllerService,
                         UserService userService,
                         BookControllerService bookControllerService) {
        this.bookingControllerService = bookingControllerService;
        this.librarianControllerService = librarianControllerService;
        this.userService = userService;
        this.bookControllerService = bookControllerService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    public void reserveBook(Long idCopy, String nickName) {
        if (idCopy != null && nickName != null && !nickName.isEmpty()) {
            bookingControllerService.reserveBook(idCopy, nickName);
        } else throw new DataIsNotCorrectException("Try again.");
    }

    public List<OnlineOrderModel> getAllUserOrders(String nickName) {
        if (nickName != null && !nickName.isEmpty()) {
            return bookingControllerService.getListOfReservedBooksByUserId(nickName);
        } else throw new DataIsNotCorrectException("Try again later.");
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    public void cancelOrder( Long idCopy, String nickName) {
        var user = userService.getUserByNickName(nickName);
        bookingControllerService.cancelOrder(idCopy, user);
    }

    public List<OnlineOrderModel> getListOfCompletedOrdersByUser(String nickName) {
        if (nickName != null && !nickName.isEmpty()) {
            return librarianControllerService.getListOfCompletedOrdersByUser(nickName);
        } else throw new DataIsNotCorrectException("Check the correctness of the entered data and try again.");
    }

    public List<BookCatalog> getAllBook() {
        return bookControllerService.getAllBook();
    }

    public List<CopiesOfBooks> findAllCopy(Long copyId) {
        if (copyId != null) {
            return bookControllerService.findAllCopy(copyId);
        } else throw new DataIsNotCorrectException("Check the correctness of the entered data and try again.");
    }
}
