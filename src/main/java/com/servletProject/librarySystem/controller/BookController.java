package com.servletProject.librarySystem.controller;

import com.servletProject.librarySystem.domen.BookCatalog;
import com.servletProject.librarySystem.domen.CopiesOfBooks;
import com.servletProject.librarySystem.facade.BookingFacade;
import com.servletProject.librarySystem.facade.SearchActionsFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("book")
public class BookController {

    private final BookingFacade bookingFacade;
    private final SearchActionsFacade searchActionsFacade;

    public BookController(BookingFacade bookingFacade, SearchActionsFacade searchActionsFacade) {
        this.bookingFacade = bookingFacade;
        this.searchActionsFacade = searchActionsFacade;
    }

    @GetMapping("catalog")
    public List<BookCatalog> getAllBooks() {
        return bookingFacade.getAllBook();
    }

    @GetMapping("detail/{id}")
    public List<CopiesOfBooks> showDetail(@PathVariable("id") Long idCopy) {
        return bookingFacade.findAllCopy(idCopy);
    }

    @GetMapping("by-title/{title}")
    public List<BookCatalog> getAllBooksByTitle(@PathVariable("title") String bookTitle) {
        return searchActionsFacade.searchBookByTitle(bookTitle);
    }

    @GetMapping("by-author/{author}")
    public List<BookCatalog> getAllBooksByAuthor(@PathVariable("author") String bookAuthor) {
        return searchActionsFacade.searchBookByAuthor(bookAuthor);
    }

    @GetMapping("by-genre/{genre}")
    public List<BookCatalog> getAllBooksByGenre(@PathVariable("genre") String bookGenre) {
        return searchActionsFacade.searchBookByGenre(bookGenre);
    }
}
