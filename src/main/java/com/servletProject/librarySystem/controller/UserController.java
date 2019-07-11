package com.servletProject.librarySystem.controller;

import com.servletProject.librarySystem.domen.dto.onlineOrderBook.OnlineOrderModel;
import com.servletProject.librarySystem.domen.dto.userEntity.UserEntityModel;
import com.servletProject.librarySystem.facade.BookingFacade;
import com.servletProject.librarySystem.facade.UserFacade;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController("/user")
public class UserController {

    private final BookingFacade bookingFacade;
    private final UserFacade userFacade;

    public UserController(BookingFacade bookingFacade, UserFacade userFacade) {
        this.bookingFacade = bookingFacade;
        this.userFacade = userFacade;
    }

    @GetMapping("orders")
    public List<OnlineOrderModel> getAllOrders(Principal principal) {
        String nickName = principal.getName();
        return bookingFacade.getAllUserOrders(nickName);
    }

    @GetMapping
    public UserEntityModel accountDetail(Principal principal) {
        String nickName = principal.getName();
        return userFacade.getUserByNickName(nickName);
    }

    @GetMapping("reading")
    public List<OnlineOrderModel> getCompletedOrders(Principal principal) {
        String nickName = principal.getName();
        return bookingFacade.getListOfCompletedOrdersByUser(nickName);
    }
}
