package com.servletProject.librarySystem.controller;

import com.servletProject.librarySystem.domen.dto.onlineOrderBook.OnlineOrderModel;
import com.servletProject.librarySystem.facade.BookingFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
        List<OnlineOrderModel> allUserOrders = bookingFacade.getAllUserOrders(nickName);
        model.addAttribute("ordersList", allUserOrders);
        return "orders";
    }
}
