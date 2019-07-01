package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.domen.*;
import com.servletProject.librarySystem.domen.dto.archiveBookUsage.ArchiveBookModel;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.OnlineOrderModel;
import com.servletProject.librarySystem.service.data.*;
import com.servletProject.librarySystem.utils.CreateEntityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibrarianControllerService {
    private final UserService userService;
    private final CopiesOfBooksService copiesOfBooksService;
    private final OnlineOrderBookService orderBookService;
    private final BookCatalogService bookCatalogService;
    private final CompletedOrdersService completedOrdersService;
    private final ArchiveBookUsageService usageService;

    public LibrarianControllerService(UserService userService, CopiesOfBooksService copiesOfBooksService,
                                      OnlineOrderBookService orderBookService, BookCatalogService bookCatalogService,
                                      CompletedOrdersService completedOrdersService, ArchiveBookUsageService usageService
    ) {
        this.userService = userService;
        this.copiesOfBooksService = copiesOfBooksService;
        this.orderBookService = orderBookService;
        this.bookCatalogService = bookCatalogService;
        this.completedOrdersService = completedOrdersService;
        this.usageService = usageService;
    }

/*    Extract in BookingControllerService
    public List<OnlineOrderModel> getAllReservedBooksByUser(String email) {
        Long userId = userService.getUserIdByEmail(email);
        return ordersUtil.getListOfReservedBooksByUserId(userId);

    }*/

    @Transactional
    public void giveBookToTheReader(Long bookCopyId, Long userId, Long librarianId) {
        orderBookService.deleteOrderByCopyIdAndUserId(bookCopyId, userId);
        completedOrdersService.saveOrder(userId, librarianId, bookCopyId);
    }

    @Transactional
    public void cancelOrder(Long bookCopyId) {
        orderBookService.cancelOrder(bookCopyId);
        copiesOfBooksService.updateAvailabilityOfCopy(true, bookCopyId);
    }

    public List<OnlineOrderModel> getListOfCompletedOrdersByReader(String email) {
        Long idUser = userService.getUserIdByEmail(email);
        List<CompletedOrders> completedOrders = completedOrdersService.findAllByUserId(idUser);
        return prepareListOfCompletedOrders(completedOrders);
    }

    public List<OnlineOrderModel> getListOfAllCompletedOrders() {
        List<CompletedOrders> completedOrders = completedOrdersService.findAllCompletedOrders();
        return prepareListOfCompletedOrders(completedOrders);
    }

    private List<OnlineOrderModel> prepareListOfCompletedOrders(List<CompletedOrders> completedOrders) {
        List<Long> ordersList = getBookCopyIdFromCompletedOrdersList(completedOrders);
        List<CopiesOfBooks> copyBookList = copiesOfBooksService.findAllById(ordersList);
        List<Long> bookIdList = getBookIdFromBookCopyList(copyBookList);
        List<BookCatalog> bookCatalogList = bookCatalogService.findAllById(bookIdList);

        return CreateEntityUtil
                .createCompletedOrdersEntityList(copyBookList, bookCatalogList, completedOrders);
    }

    private List<Long> getBookIdFromBookCopyList(List<CopiesOfBooks> copyBookList) {
        return copyBookList.stream()
                .map(CopiesOfBooks::getIdBook)
                .collect(Collectors.toList());
    }

    private List<Long> getBookCopyIdFromCompletedOrdersList(List<CompletedOrders> orderBookList) {
        return orderBookList.stream()
                .map(CompletedOrders::getIdBook)
                .collect(Collectors.toList());
    }

    private List<Long> getBookCopyIdFromArchiveUsageList(List<ArchiveBookUsage> bookUsageList) {
        return bookUsageList.stream()
                .map(ArchiveBookUsage::getIdCopiesBook)
                .collect(Collectors.toList());
    }

    @Transactional
    public void returnBookToTheCatalog(Long readerId, Long copyId, String condition) {
        usageService.putBookInUsageArchive(copyId, readerId, condition);
        completedOrdersService.deleteFromCompletedOrdersByCopyId(copyId);
        copiesOfBooksService.updateCopyOfBookInfo(copyId, condition);

    }

    public List<ArchiveBookModel> getListOfActiveUsageByUser(String email) {
        UserEntity user = userService.getUserByEmail(email);
        List<ArchiveBookUsage> bookUsageList = usageService.findAllByUserId(user.getId());
        List<Long> copyIdList = getBookCopyIdFromArchiveUsageList(bookUsageList);
        List<CopiesOfBooks> copyBookList = copiesOfBooksService.findAllById(copyIdList);
        List<Long> bookIdList = getBookIdFromBookCopyList(copyBookList);
        List<BookCatalog> bookCatalogList = bookCatalogService.findAllById(bookIdList);


        return CreateEntityUtil.createArchiveBookModelEntityList(copyBookList, bookCatalogList, user);
    }

    public List<ArchiveBookModel> getListOfAllArchiveUsage() {
        List<ArchiveBookUsage> allUsageArchive = usageService.findAllUsageArchive();
        List<Long> copyIdFromArchiveUsageList = getBookCopyIdFromArchiveUsageList(allUsageArchive);
        List<CopiesOfBooks> copiesOfBooks = copiesOfBooksService.findAllById(copyIdFromArchiveUsageList);
        List<Long> bookIdList = getBookIdFromBookCopyList(copiesOfBooks);
        List<BookCatalog> bookCatalogList = bookCatalogService.findAllById(bookIdList);
        return getArchiveBookModelList(allUsageArchive, copiesOfBooks, bookCatalogList);
    }

    public List<CopiesOfBooks> unusableConditionBooksList() throws SQLException {
        return copiesOfBooksService.getAllCopyByCondition("unusable");
    }

    private List<ArchiveBookModel> getArchiveBookModelList(List<ArchiveBookUsage> allUsageArchive,
                                                           List<CopiesOfBooks> copiesOfBooks, List<BookCatalog> bookCatalogList
    ) {
        List<ArchiveBookModel> modelList = new ArrayList<>();
        for (BookCatalog book : bookCatalogList) {
            ArchiveBookModel model = getArchiveBookModelEntity(allUsageArchive, copiesOfBooks, book);
            modelList.add(model);
        }
        return modelList;
    }

    private ArchiveBookModel getArchiveBookModelEntity(List<ArchiveBookUsage> allUsageArchive,
                                                       List<CopiesOfBooks> copiesOfBooks, BookCatalog book
    ) {
        ArchiveBookModel model = new ArchiveBookModel();
        CopiesOfBooks copy = CreateEntityUtil.getCopiesOfBooksId(copiesOfBooks, book);
        model.setUniqueId(copy.getId());
        ArchiveBookUsage archiveBookUsage = CreateEntityUtil.getArchiveBookUsage(allUsageArchive, copy);
        model.setName(userService.getFullName(archiveBookUsage.getIdReader()));
        model.setUserId(archiveBookUsage.getIdReader());
        model.setBookTitle(book.getBookTitle());
        model.setBookAuthor(book.getBookAuthor());
        model.setGenre(book.getGenre());
        model.setYearOfPublication(book.getYearOfPublication());
        return model;
    }
}