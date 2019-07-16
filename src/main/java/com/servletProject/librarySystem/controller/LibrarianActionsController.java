package com.servletProject.librarySystem.controller;

import com.servletProject.librarySystem.domen.CopiesOfBooks;
import com.servletProject.librarySystem.domen.dto.archiveBookUsage.ArchiveBookModel;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.CancelConfirmOrderModel;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.OnlineOrderModel;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.ReturnOrderInCatalogModel;
import com.servletProject.librarySystem.service.BookingControllerService;
import com.servletProject.librarySystem.service.LibrarianControllerService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("orders")
@PreAuthorize("hasAuthority('LIBRARIAN')")
public class LibrarianActionsController {

    private final LibrarianControllerService librarianControllerService;
    private final BookingControllerService bookingControllerService;

    public LibrarianActionsController(LibrarianControllerService librarianControllerService,
                                      BookingControllerService bookingControllerService
    ) {
        this.librarianControllerService = librarianControllerService;
        this.bookingControllerService = bookingControllerService;
    }

    @GetMapping("/orders")
    public List<OnlineOrderModel> getAllOrders(Principal principal) {
        String librarian = principal.getName();
        return bookingControllerService.getListOfAllReservedBooks(librarian);
    }

    @GetMapping("{email}")
    public List<OnlineOrderModel> getOrdersByUserEmail(@PathVariable("email") String userEmail,
                                                       Principal principal) {
        String librarian = principal.getName();
        return bookingControllerService
                .getListOfReservedBooksByUserEmail(librarian, userEmail);
    }

    @PostMapping("cancel")
    @ResponseStatus(HttpStatus.OK)
    public void cancelOrder(@Valid @RequestBody CancelConfirmOrderModel cancelOrderModel,
                            Principal principal) {
        String librarian = principal.getName();
        librarianControllerService
                .cancelOrder(cancelOrderModel.getIdCopy(), cancelOrderModel.getIdUser(), librarian);
    }

    @PostMapping("confirm")
    @ResponseStatus(HttpStatus.OK)
    public void confirmOrder(@Valid @RequestBody CancelConfirmOrderModel confirmOrderModel,
                             Principal principal) {
        String librarian = principal.getName();
        librarianControllerService
                .giveBookToTheReader(confirmOrderModel.getIdCopy(), confirmOrderModel.getIdUser(), librarian);
    }

    @GetMapping("completed")
    public List<OnlineOrderModel> getAllCompletedOrders(Principal principal) {
        String librarian = principal.getName();
        return librarianControllerService.getListOfAllCompletedOrders(librarian);

    }

    @GetMapping("completed/{email}")
    public List<OnlineOrderModel> getCompletedOrdersByEmail(@PathVariable("email") String userEmail,
                                                            Principal principal) {
        String librarian = principal.getName();
        return librarianControllerService
                .getListOfCompletedOrdersByReader(userEmail, librarian);

    }

    @PostMapping("return")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void returnBookToTheCatalog(@Valid @RequestBody ReturnOrderInCatalogModel model,
                                       Principal principal) {
        String librarian = principal.getName();
        librarianControllerService.returnBookToTheCatalog(model, librarian);
    }

    @GetMapping("archive")
    public List<ArchiveBookModel> getAllArchiveUsage(Principal principal) {
        String librarian = principal.getName();
        return librarianControllerService.getListOfAllArchiveUsage(librarian);

    }

    @GetMapping("archive/{email}")
    public List<ArchiveBookModel> archiveUsageByUser(@PathVariable("email") String userEmail,
                                                     Principal principal) {
        String librarian = principal.getName();
        return librarianControllerService.getListOfActiveUsageByUser(userEmail, librarian);
    }

    @GetMapping("unusable")
    public List<CopiesOfBooks> getAllUnusableBooks(Principal principal) {
        String librarian = principal.getName();
        return librarianControllerService.unusableConditionBooksList(librarian);
    }

    @PostMapping("remove/{idCopy}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void removeUnusable(@PathVariable("idCopy") Long idCopy,
                               Principal principal) {
        String librarian = principal.getName();
        librarianControllerService.deleteUnusableBookCopy(idCopy, librarian);
    }
}
