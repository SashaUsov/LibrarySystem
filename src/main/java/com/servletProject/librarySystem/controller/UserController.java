package com.servletProject.librarySystem.controller;

import com.servletProject.librarySystem.domen.dto.userEntity.UserEntityModel;
import com.servletProject.librarySystem.facade.BookingFacade;
import com.servletProject.librarySystem.facade.UserFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final BookingFacade bookingFacade;
    private final UserFacade userFacade;

    public UserController(BookingFacade bookingFacade, UserFacade userFacade) {
        this.bookingFacade = bookingFacade;
        this.userFacade = userFacade;
    }

    @GetMapping("orders")
    public String getAllOrders(Principal principal,
                               Model model
    ) {
        String nickName = principal.getName();
        model.addAttribute("ordersList", bookingFacade.getAllUserOrders(nickName));
        return "orders";
    }

    @GetMapping
    public String accountDetail(Principal principal,
                                Model model) {
        String nickName = principal.getName();
        UserEntityModel user = userFacade.getUserByNickName(nickName);
        model.addAttribute("user_info", user);
        return "info";
    }

    @GetMapping("reading")
    public String getCompletedOrders(Principal principal,
                                     Model model
    ) {
        String nickName = principal.getName();
        model.addAttribute("readingList", bookingFacade.getListOfCompletedOrdersByUser(nickName));
        return "reading";
    }
}
