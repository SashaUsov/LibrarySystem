package com.servletProject.librarySystem.controller;

import com.servletProject.librarySystem.exception.DataIsNotCorrectException;
import com.servletProject.librarySystem.facade.BookingFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/booking")
public class BookingController {

    private final BookingFacade bookingFacade;

    public BookingController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @PostMapping
    public String reserveBookCopy(@RequestParam Long idCopy,
                                  @RequestParam String nickName,
                                  Model model
    ) {
        bookingFacade.reserveBook(idCopy, nickName);
        model.addAttribute("ordersList", bookingFacade.getAllUserOrders(nickName));
        return "orders";
    }

    @PostMapping("cancel")
    public String cancelOrder(@RequestParam Long idCopy,
                                  @RequestParam String nickName,
                                  Model model
    ) {
       if (nickName != null && idCopy !=null && !nickName.isEmpty()) {
           bookingFacade.cancelOrder(idCopy, nickName);
           model.addAttribute("ordersList", bookingFacade.getAllUserOrders(nickName));
           return "orders";
       } else throw  new DataIsNotCorrectException("Enter correct data and try again.");
    }
}
