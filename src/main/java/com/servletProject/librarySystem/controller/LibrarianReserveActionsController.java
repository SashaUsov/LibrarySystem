package com.servletProject.librarySystem.controller;

import com.servletProject.librarySystem.domen.dto.onlineOrderBook.OnlineOrderModel;
import com.servletProject.librarySystem.exception.DataIsNotCorrectException;
import com.servletProject.librarySystem.service.BookingControllerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("librarian-reserve")
public class LibrarianReserveActionsController {

    private final BookingControllerService bookingControllerService;

    public LibrarianReserveActionsController(BookingControllerService bookingControllerService) {
        this.bookingControllerService = bookingControllerService;
    }

    @GetMapping("all")
    public List<OnlineOrderModel> getAllReservedBooks() {
        return bookingControllerService.getListOfAllReservedBooks();
    }

    @GetMapping("all/{email}")
    public List<OnlineOrderModel> getAllReservedBooksByUserEmail(@PathVariable("email") String email) {
        if (email != null && !email.isEmpty()) {
            return bookingControllerService.getListOfReservedBooksByUserEmail(email);
        } else throw new DataIsNotCorrectException("Enter the correct email and try again.");
    }
}
