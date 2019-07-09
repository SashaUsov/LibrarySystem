package com.servletProject.librarySystem.facade;

import com.servletProject.librarySystem.domen.dto.onlineOrderBook.OnlineOrderModel;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.ReturnOrderInCatalogModel;
import com.servletProject.librarySystem.exception.DataIsNotCorrectException;
import com.servletProject.librarySystem.service.BookingControllerService;
import com.servletProject.librarySystem.service.LibrarianControllerService;
import com.servletProject.librarySystem.utils.OrdersUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

@Service
public class LibrarianOrdersActionsFacade {

    private final LibrarianControllerService librarianControllerService;

    public LibrarianOrdersActionsFacade(LibrarianControllerService librarianControllerService) {
        this.librarianControllerService = librarianControllerService;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    public void returnOrderToTheReader(@Valid @RequestBody ReturnOrderInCatalogModel model) {
        librarianControllerService.returnBookToTheCatalog(model);
    }

    public List<OnlineOrderModel> getAllCompletedOrders() {
        return librarianControllerService.getListOfAllCompletedOrders();
    }

    public List<OnlineOrderModel> getAllCompletedOrdersByEmail(String email) {
        if (OrdersUtil.ifEmailPresent(email)) {
            return librarianControllerService.getListOfCompletedOrdersByReader(email);
        } else throw new DataIsNotCorrectException("Enter the correct email and try again.");
    }
}
