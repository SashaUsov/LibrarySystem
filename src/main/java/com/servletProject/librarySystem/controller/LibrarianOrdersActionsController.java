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

    public LibrarianOrdersActionsController(LibrarianControllerService librarianControllerService) {
        this.librarianControllerService = librarianControllerService;
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
}
