package com.servletProject.librarySystem.controller;

import com.servletProject.librarySystem.domen.dto.bookCatalog.CreateBookCatalogModel;
import com.servletProject.librarySystem.facade.LibrarianCatalogActionsFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/librarian")
public class LibrarianController {

    private final LibrarianCatalogActionsFacade librarianCatalogActionsFacade;

    public LibrarianController(LibrarianCatalogActionsFacade librarianCatalogActionsFacade) {
        this.librarianCatalogActionsFacade = librarianCatalogActionsFacade;
    }

    @GetMapping
    public String librarianPage() {
        return "librarian";
    }

    @PostMapping
    public String addBook(@Valid CreateBookCatalogModel model) {
        librarianCatalogActionsFacade.addNewBook(model);
        return "librarian";
    }

}
