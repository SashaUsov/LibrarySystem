package com.servletProject.librarySystem.facade;

import com.servletProject.librarySystem.domen.dto.onlineOrderBook.IssueOrderModel;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.OnlineOrderModel;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.ReturnOrderInCatalogModel;
import com.servletProject.librarySystem.exception.DataIsNotCorrectException;
import com.servletProject.librarySystem.service.BookingControllerService;
import com.servletProject.librarySystem.service.LibrarianControllerService;
import com.servletProject.librarySystem.utils.OrdersUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Service
public class LibrarianOrdersActionsController {

    private final LibrarianControllerService librarianControllerService;
    private final BookingControllerService bookingControllerService;

    public LibrarianOrdersActionsController(LibrarianControllerService librarianControllerService,
                                            BookingControllerService bookingControllerService
    ) {
        this.librarianControllerService = librarianControllerService;
        this.bookingControllerService = bookingControllerService;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    public void giveBookToTheReader(@Valid @RequestBody IssueOrderModel model) {
        librarianControllerService.giveBookToTheReader(model);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    public void returnOrderToTheReader(@Valid @RequestBody ReturnOrderInCatalogModel model) {
        librarianControllerService.returnBookToTheCatalog(model);
    }

    @ResponseStatus(HttpStatus.OK)
    public void cancelOrder(Long idCopy) {
        if (idCopy != null) {
            librarianControllerService.cancelOrder(idCopy);
        } else throw new DataIsNotCorrectException("A book with this id does not exist in the orders table.");
    }

    public List<OnlineOrderModel> getAllReservedBooks() {
        return bookingControllerService.getListOfAllReservedBooks();
    }

    public List<OnlineOrderModel> getAllReservedBooksByUserEmail(String email) {
        if (OrdersUtil.ifEmailPresent(email)) {
            return bookingControllerService.getListOfReservedBooksByUserEmail(email);
        } else throw new DataIsNotCorrectException("Enter the correct email and try again.");
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
