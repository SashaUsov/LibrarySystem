package com.servletProject.librarySystem.controller;

import com.servletProject.librarySystem.domen.BookCatalog;
import com.servletProject.librarySystem.service.BookControllerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("search")
public class SearchController {

    private final BookControllerService bookControllerService;

    public SearchController(BookControllerService bookControllerService) {
        this.bookControllerService = bookControllerService;
    }

    @GetMapping("by-title/{title}")
    public List<BookCatalog> searchBookByTitle(@PathVariable("title") String title) {
        return bookControllerService.getAllBookByTitle(title);
    }

    @GetMapping("by-author/{author}")
    public List<BookCatalog> searchBookByAuthor(@PathVariable("author") String author) {
        return bookControllerService.getAllBookByAuthor(author);
    }

    @GetMapping("by-genre/{genre}")
    public List<BookCatalog> searchBookByGenre(@PathVariable("genre") String genre) {
        return bookControllerService.getAllBookByGenre(genre);
    }
}
