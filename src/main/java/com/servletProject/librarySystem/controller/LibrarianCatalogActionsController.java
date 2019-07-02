package com.servletProject.librarySystem.controller;

import com.servletProject.librarySystem.domen.CopiesOfBooks;
import com.servletProject.librarySystem.domen.dto.bookCatalog.CreateBookCatalogModel;
import com.servletProject.librarySystem.exception.DataIsNotCorrectException;
import com.servletProject.librarySystem.service.BookControllerService;
import com.servletProject.librarySystem.service.LibrarianControllerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("librarian-catalog")
public class LibrarianCatalogActionsController {
    private final BookControllerService bookControllerService;
    private final LibrarianControllerService librarianControllerService;

    public LibrarianCatalogActionsController(BookControllerService bookControllerService,
                                             LibrarianControllerService librarianControllerService
    ) {
        this.bookControllerService = bookControllerService;
        this.librarianControllerService = librarianControllerService;
    }

    @PostMapping("add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewBook(@Valid @RequestBody CreateBookCatalogModel model) {
        bookControllerService.addNewBook(model);
    }

    @GetMapping("unusable")
    public List<CopiesOfBooks> getBooksInUnusableCondition() {
        return librarianControllerService.unusableConditionBooksList();
    }

    @DeleteMapping("unusable/delete/{copy_id}")
    public void deleteUnusableBookCopy(@PathVariable("copy_id") Long idCopy) {
        if (idCopy != null) {
            librarianControllerService.deleteUnusableBookCopy(idCopy);
        } else throw new DataIsNotCorrectException("A book with this id does not exist in the catalog.");
    }
}
