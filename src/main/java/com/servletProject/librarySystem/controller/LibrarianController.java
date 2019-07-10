package com.servletProject.librarySystem.controller;

import com.servletProject.librarySystem.domen.dto.bookCatalog.CreateBookCatalogModel;
import com.servletProject.librarySystem.service.BookControllerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/librarian")
public class LibrarianController {

    private final BookControllerService bookControllerService;

    public LibrarianController(BookControllerService bookControllerService) {
        this.bookControllerService = bookControllerService;
    }


    @GetMapping
    public String librarianPage() {
        return "librarian";
    }

    @PostMapping
    public String addBook(@Valid CreateBookCatalogModel model,
                          Model m) {
        bookControllerService.addNewBook(model);
        m.addAttribute("message", "Book successfully added to catalog.");
        return "librarian";
    }

}
