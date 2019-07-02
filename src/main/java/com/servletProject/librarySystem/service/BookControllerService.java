package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.domen.BookCatalog;
import com.servletProject.librarySystem.domen.dto.bookCatalog.CreateBookCatalogModel;
import com.servletProject.librarySystem.exception.DataIsNotCorrectException;
import com.servletProject.librarySystem.service.data.BookCatalogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookControllerService {
    private final BookCatalogService bookCatalogService;

    public BookControllerService(BookCatalogService bookCatalogService) {
        this.bookCatalogService = bookCatalogService;
    }

    public List<BookCatalog> getAllBookByTitle(String title){
        return bookCatalogService.getAllBookByTitle(title);
    }

    public List<BookCatalog> getAllBookByAuthor(String author){
        return bookCatalogService.getAllBookByAuthor(author);
    }

    public List<BookCatalog> getAllBookByGenre(String genre){
        return bookCatalogService.getAllBookByGenre(genre);
    }

    public void addNewBook(CreateBookCatalogModel model) {
        if (model != null) {
            bookCatalogService.saveBook(model);
        } else throw new DataIsNotCorrectException("Check the correctness of filling out the form and try again.");
    }
}
