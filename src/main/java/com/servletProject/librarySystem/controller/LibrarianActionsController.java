package com.servletProject.librarySystem.controller;

import com.servletProject.librarySystem.exception.DataIsNotCorrectException;
import com.servletProject.librarySystem.service.BookingControllerService;
import com.servletProject.librarySystem.service.LibrarianControllerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/orders")
public class LibrarianActionsController {

    private final LibrarianControllerService librarianControllerService;
    private final BookingControllerService bookingControllerService;

    public LibrarianActionsController(LibrarianControllerService librarianControllerService,
                                      BookingControllerService bookingControllerService
    ) {
        this.librarianControllerService = librarianControllerService;
        this.bookingControllerService = bookingControllerService;
    }

    @GetMapping
    public String getAllOrders(Principal principal,
                               Model model
    ) {
        String librarian = principal.getName();
        model.addAttribute("ordersList", bookingControllerService.getListOfAllReservedBooks(librarian));
        return "work";
    }

    @GetMapping("by-email")
    public String getOrdersByUserEmail(@RequestParam String userEmail,
                                       Principal principal,
                                       Model model
    ) {
        String librarian = principal.getName();
        model.addAttribute("ordersList",
                bookingControllerService.getListOfReservedBooksByUserEmail(librarian, userEmail));
        return "work";
    }

    @PostMapping("cancel")
    @ResponseStatus(HttpStatus.OK)
    public String cancelOrder(@RequestParam Long idUser,
                              @RequestParam Long idCopy,
                              Principal principal,
                              Model model
    ) {
        String librarian = principal.getName();
        if (idUser != null && idCopy != null) {
            librarianControllerService.cancelOrder(idCopy, idUser, librarian);
            model.addAttribute("message", "Order canceled!");
            return "librarian";
        } else throw new DataIsNotCorrectException("Reload page and try again.");
    }

    @PostMapping("finish")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String confirmOrder(@RequestParam Long idUser,
                              @RequestParam Long idCopy,
                              Principal principal,
                              Model model
    ) {
        String librarian = principal.getName();
        if (idUser != null && idCopy != null) {
            librarianControllerService.giveBookToTheReader(idCopy, idUser, librarian);
            model.addAttribute("message", "Order confirmed");
            return "librarian";
        } else throw new DataIsNotCorrectException("Reload page and try again.");
    }
}
