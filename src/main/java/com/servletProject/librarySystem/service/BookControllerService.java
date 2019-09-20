package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.domen.BookCatalog;
import com.servletProject.librarySystem.domen.CopiesOfBooks;
import com.servletProject.librarySystem.domen.dto.bookCatalog.CreateBookCatalogModel;
import com.servletProject.librarySystem.exception.DataIsNotCorrectException;
import com.servletProject.librarySystem.service.data.BookCatalogService;
import com.servletProject.librarySystem.service.data.CopiesOfBooksService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookControllerService {
    private final BookCatalogService bookCatalogService;
    private final CopiesOfBooksService copiesOfBooksService;

    public BookControllerService(BookCatalogService bookCatalogService, CopiesOfBooksService copiesOfBooksService) {
        this.bookCatalogService = bookCatalogService;
        this.copiesOfBooksService = copiesOfBooksService;
    }

    @Transactional
    public void addNewBook(CreateBookCatalogModel model) {
        if (model != null) {
            bookCatalogService.saveBook(model);
        } else throw new DataIsNotCorrectException("Check the correctness of filling out the form and try again.");
    }

    public List<BookCatalog> getAllBook() {
        return bookCatalogService.getAllBook();
    }

    public List<CopiesOfBooks> findAllCopy(Long copyId) {
        return copiesOfBooksService.findAllCopy(copyId);
    }
}
