package com.servletProject.librarySystem.controller;

import com.servletProject.librarySystem.domen.dto.archiveBookUsage.ArchiveBookModel;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.OnlineOrderModel;
import com.servletProject.librarySystem.domen.dto.userEntity.UserEntityModel;
import com.servletProject.librarySystem.exception.DataIsNotCorrectException;
import com.servletProject.librarySystem.service.BookingControllerService;
import com.servletProject.librarySystem.service.LibrarianControllerService;
import com.servletProject.librarySystem.service.UserControllerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("booking")
public class BookingController {

    private final BookingControllerService bookingControllerService;
    private final LibrarianControllerService librarianControllerService;
    private final UserControllerService userControllerService;

    public BookingController(BookingControllerService bookingControllerService,
                             LibrarianControllerService librarianControllerService,
                             UserControllerService userControllerService
    ) {
        this.bookingControllerService = bookingControllerService;
        this.librarianControllerService = librarianControllerService;
        this.userControllerService = userControllerService;
    }

    @PostMapping("{book_copy_id}/{user_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void reserveBook(@PathVariable("book_copy_id") Long idCopy,
                            @PathVariable("user_id") Long idUser
    ) {
        bookingControllerService.reserveBook(idCopy, idUser);
    }

    @GetMapping("{user_id}")
    public List<OnlineOrderModel> getAllUserOrders(@PathVariable("user_id") Long idUser) {
        return bookingControllerService.getListOfReservedBooksByUserId(idUser);
    }

    @PostMapping("cancel-order/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void cancelOrder(@PathVariable("id") Long idCopy) {
        bookingControllerService.cancelOrder(idCopy);
    }

    @GetMapping("completed/orders/{id}")
    public List<OnlineOrderModel> getListOfCompletedOrdersByUserId(@PathVariable("id") Long id) {
        if (id != null) {
            return librarianControllerService.getListOfCompletedOrdersByUserId(id);
        } else throw new DataIsNotCorrectException("Check the correctness of the entered data and try again.");
    }

    @GetMapping("my-archive/{id}")
    public List<ArchiveBookModel> getListOfArchiveUsageByUserId(@PathVariable("id") Long id) {
        if (id != null) {
            UserEntityModel user = userControllerService.findUser(id);
            return librarianControllerService.getListOfActiveUsageByUser(user.getMail());
        } else throw new DataIsNotCorrectException("Check the correctness of the entered data and try again.");
    }
}
