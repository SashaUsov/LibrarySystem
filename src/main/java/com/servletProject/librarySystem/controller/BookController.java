package com.servletProject.librarySystem.controller;

import com.servletProject.librarySystem.facade.BookingFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BookingFacade bookingFacade;

    public BookController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
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

}
