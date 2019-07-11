package com.servletProject.librarySystem.controller;

import com.servletProject.librarySystem.domen.BookCatalog;
import com.servletProject.librarySystem.domen.dto.bookCatalog.CreateBookCatalogModel;
import com.servletProject.librarySystem.service.BookControllerService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController("/librarian")
@PreAuthorize("hasAuthority('LIBRARIAN')")
public class LibrarianController {

    private final BookControllerService bookControllerService;

    public LibrarianController(BookControllerService bookControllerService) {
        this.bookControllerService = bookControllerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<BookCatalog> addBook(@Valid @RequestBody CreateBookCatalogModel model) {
        bookControllerService.addNewBook(model);
        return bookControllerService.getAllBook();
    }

}
