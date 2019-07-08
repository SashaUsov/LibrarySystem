package com.servletProject.librarySystem.controller;

import com.servletProject.librarySystem.facade.BookingFacade;
import com.servletProject.librarySystem.facade.SearchActionsFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BookingFacade bookingFacade;
    private final SearchActionsFacade searchActionsFacade;

    public BookController(BookingFacade bookingFacade, SearchActionsFacade searchActionsFacade) {
        this.bookingFacade = bookingFacade;
        this.searchActionsFacade = searchActionsFacade;
    }

    @GetMapping("catalog")
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookingFacade.getAllBook());
        return "catalog";
    }

    @GetMapping("search")
    public String search() {
        return "booksearch";
    }

    @GetMapping("detail")
    public String showDetail(@RequestParam Long idCopy, Model model) {
        model.addAttribute("booksCopy", bookingFacade.findAllCopy(idCopy));
        return "detail";
    }

    @GetMapping("by-title")
    public String getAllBooksByTitle(@RequestParam String bookTitle, Model model) {
        model.addAttribute("books", searchActionsFacade.searchBookByTitle(bookTitle));
        return "catalog";
    }

    @GetMapping("by-author")
    public String getAllBooksByAuthor(@RequestParam String bookAuthor, Model model) {
        model.addAttribute("books", searchActionsFacade.searchBookByAuthor(bookAuthor));
        return "catalog";
    }

    @GetMapping("by-genre")
    public String getAllBooksByGenre(@RequestParam String bookGenre, Model model) {
        model.addAttribute("books", searchActionsFacade.searchBookByGenre(bookGenre));
        return "catalog";
    }
}
