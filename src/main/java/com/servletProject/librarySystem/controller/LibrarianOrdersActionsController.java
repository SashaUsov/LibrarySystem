package com.servletProject.librarySystem.controller;

import com.servletProject.librarySystem.domen.dto.onlineOrderBook.IssueOrderModel;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.OnlineOrderModel;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.ReturnOrderInCatalogModel;
import com.servletProject.librarySystem.exception.DataIsNotCorrectException;
import com.servletProject.librarySystem.service.BookingControllerService;
import com.servletProject.librarySystem.service.LibrarianControllerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("librarian-order")
public class LibrarianOrdersActionsController {

    private final LibrarianControllerService librarianControllerService;
    private final BookingControllerService bookingControllerService;

    public LibrarianOrdersActionsController(LibrarianControllerService librarianControllerService,
                                            BookingControllerService bookingControllerService
    ) {
        this.librarianControllerService = librarianControllerService;
        this.bookingControllerService = bookingControllerService;
    }

    @PostMapping("to-issue")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void giveBookToTheReader(@Valid @RequestBody IssueOrderModel model) {
        librarianControllerService.giveBookToTheReader(model);
    }

    @PostMapping("return")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void returnOrderToTheReader(@Valid @RequestBody ReturnOrderInCatalogModel model) {
        librarianControllerService.returnBookToTheCatalog(model);
    }

    @PostMapping("cancel/{copy_id}")
    @ResponseStatus(HttpStatus.OK)
    public void cancelOrder(@PathVariable("copy_id") Long idCopy) {
        if (idCopy != null) {
            librarianControllerService.cancelOrder(idCopy);
        } else throw new DataIsNotCorrectException("A book with this id does not exist in the orders table.");
    }

    @GetMapping("all")
    public List<OnlineOrderModel> getAllReservedBooks() {
        return bookingControllerService.getListOfAllReservedBooks();
    }

    @GetMapping("all/{email}")
    public List<OnlineOrderModel> getAllReservedBooksByUserEmail(@PathVariable("email") String email) {
        if (ifEmailPresent(email)) {
            return bookingControllerService.getListOfReservedBooksByUserEmail(email);
        } else throw new DataIsNotCorrectException("Enter the correct email and try again.");
    }

    @GetMapping("completed")
    public List<OnlineOrderModel> getAllCompletedOrders() {
            return librarianControllerService.getListOfAllCompletedOrders();
    }

    @GetMapping("completed/{email}")
    public List<OnlineOrderModel> getAllCompletedOrdersByEmail(@PathVariable("email") String email) {
        if (ifEmailPresent(email)) {
            return librarianControllerService.getListOfCompletedOrdersByReader(email);
        } else throw new DataIsNotCorrectException("Enter the correct email and try again.");
    }

    private boolean ifEmailPresent(String email) {
        return (email != null && !email.isEmpty());
    }
}
