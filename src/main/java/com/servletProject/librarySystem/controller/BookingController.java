package com.servletProject.librarySystem.controller;

import com.servletProject.librarySystem.domen.dto.onlineOrderBook.CreateCancelOrderModel;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.OnlineOrderModel;
import com.servletProject.librarySystem.facade.BookingFacade;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController("/booking")
public class BookingController {

    private final BookingFacade bookingFacade;

    public BookingController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @PostMapping("reserve")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<OnlineOrderModel> reserveBookCopy(@Valid @RequestBody CreateCancelOrderModel orderModel) {
        bookingFacade.reserveBook(orderModel.getIdCopy(), orderModel.getNickName());
        return bookingFacade.getAllUserOrders(orderModel.getNickName());
    }

    @PostMapping("revoke")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<OnlineOrderModel> cancelOrder(@Valid @RequestBody CreateCancelOrderModel orderModel) {
        bookingFacade.cancelOrder(orderModel.getIdCopy(), orderModel.getNickName());
        return bookingFacade.getAllUserOrders(orderModel.getNickName());
    }
}
