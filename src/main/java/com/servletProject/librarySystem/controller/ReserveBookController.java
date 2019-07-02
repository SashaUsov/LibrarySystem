package com.servletProject.librarySystem.controller;

import com.servletProject.librarySystem.domen.dto.onlineOrderBook.OnlineOrderModel;
import com.servletProject.librarySystem.service.BookingControllerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("booking")
public class ReserveBookController {

    private final BookingControllerService bookingControllerService;

    public ReserveBookController(BookingControllerService bookingControllerService) {
        this.bookingControllerService = bookingControllerService;
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
}
