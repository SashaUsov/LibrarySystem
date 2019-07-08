package com.servletProject.librarySystem.facade;

import com.servletProject.librarySystem.domen.CopiesOfBooks;
import com.servletProject.librarySystem.domen.dto.bookCatalog.CreateBookCatalogModel;
import com.servletProject.librarySystem.exception.DataIsNotCorrectException;
import com.servletProject.librarySystem.service.BookControllerService;
import com.servletProject.librarySystem.service.LibrarianControllerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
public class LibrarianCatalogActionsFacade {
    private final BookControllerService bookControllerService;
    private final LibrarianControllerService librarianControllerService;

    public LibrarianCatalogActionsFacade(BookControllerService bookControllerService,
                                         LibrarianControllerService librarianControllerService
    ) {
        this.bookControllerService = bookControllerService;
        this.librarianControllerService = librarianControllerService;
    }

    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewBook(CreateBookCatalogModel model) {
        bookControllerService.addNewBook(model);
    }

    @GetMapping("unusable")
    public List<CopiesOfBooks> getBooksInUnusableCondition() {
        return librarianControllerService.unusableConditionBooksList();
    }

    public void deleteUnusableBookCopy(Long idCopy) {
        if (idCopy != null) {
            librarianControllerService.deleteUnusableBookCopy(idCopy);
        } else throw new DataIsNotCorrectException("Enter the correct data and try again.");
    }
}
