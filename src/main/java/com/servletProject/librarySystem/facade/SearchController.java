package com.servletProject.librarySystem.facade;

import com.servletProject.librarySystem.domen.BookCatalog;
import com.servletProject.librarySystem.service.BookControllerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchController {

    private final BookControllerService bookControllerService;

    public SearchController(BookControllerService bookControllerService) {
        this.bookControllerService = bookControllerService;
    }

    public List<BookCatalog> searchBookByTitle(String title) {
        return bookControllerService.getAllBookByTitle(title);
    }

    public List<BookCatalog> searchBookByAuthor(String author) {
        return bookControllerService.getAllBookByAuthor(author);
    }

    public List<BookCatalog> searchBookByGenre(String genre) {
        return bookControllerService.getAllBookByGenre(genre);
    }
}
