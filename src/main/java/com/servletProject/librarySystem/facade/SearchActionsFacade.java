package com.servletProject.librarySystem.facade;

import com.servletProject.librarySystem.domen.BookCatalog;
import com.servletProject.librarySystem.exception.DataIsNotCorrectException;
import com.servletProject.librarySystem.service.data.BookCatalogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchActionsFacade {

    private final BookCatalogService bookCatalogService;

    public SearchActionsFacade(BookCatalogService bookCatalogService) {
        this.bookCatalogService = bookCatalogService;
    }

    public List<BookCatalog> searchBookByTitle(String title) {
        if (title != null && !title.isEmpty()) {
            return bookCatalogService.getAllBookByTitle(title);
        } else throw new DataIsNotCorrectException("Enter the correct data and try again.");
    }

    public List<BookCatalog> searchBookByAuthor(String author) {
        if (author != null && !author.isEmpty()) {
            return bookCatalogService.getAllBookByAuthor(author);
        } else throw new DataIsNotCorrectException("Enter the correct data and try again.");
    }

    public List<BookCatalog> searchBookByGenre(String genre) {
        if (genre != null && !genre.isEmpty()) {
            return bookCatalogService.getAllBookByGenre(genre);
        } else throw new DataIsNotCorrectException("Enter the correct data and try again.");
    }
}
