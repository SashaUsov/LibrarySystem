package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.domen.*;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.IssueOrderModel;
import com.servletProject.librarySystem.domen.dto.archiveBookUsage.ArchiveBookModel;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.OnlineOrderModel;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.ReturnOrderInCatalogModel;
import com.servletProject.librarySystem.exception.UserNotFoundException;
import com.servletProject.librarySystem.service.data.*;
import com.servletProject.librarySystem.utils.CreateEntityUtil;
import com.servletProject.librarySystem.utils.OrdersUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void giveBookToTheReader(IssueOrderModel model) {
        Long bookCopyId = model.getIdCopy();
        Long userId= model.getIdReader();
        Long librarianId = model.getIdLibrarian();

        orderBookService.deleteOrderByCopyIdAndUserId(bookCopyId, userId);
        completedOrdersService.saveOrder(userId, librarianId, bookCopyId);
    }

    public List<OnlineOrderModel> getListOfCompletedOrdersByReader(String email) {
        Long idUser = userService.getUserIdByEmail(email);
        List<CompletedOrders> completedOrders = completedOrdersService.findAllByUserId(idUser);
        return prepareListOfCompletedOrders(completedOrders);
    }

    public List<OnlineOrderModel> getListOfCompletedOrdersByUserId(Long id) {
        if (userService.isUserExist(id)) {
            List<CompletedOrders> completedOrders = completedOrdersService.findAllByUserId(id);
            return prepareListOfCompletedOrders(completedOrders);
        } else throw new UserNotFoundException("The user you are looking for does not exist.");
    }

    public List<OnlineOrderModel> getListOfAllCompletedOrders() {
        List<CompletedOrders> completedOrders = completedOrdersService.findAllCompletedOrders();
        return prepareListOfCompletedOrders(completedOrders);
    }

    public List<ArchiveBookModel> getListOfActiveUsageByUser(String email) {
        UserEntity user = userService.getUserByEmail(email);
        List<ArchiveBookUsage> bookUsageList = usageService.findAllByUserId(user.getId());
        List<Long> copyIdList = getBookCopyIdFromArchiveUsageList(bookUsageList);
        List<CopiesOfBooks> copyBookList = copiesOfBooksService.findAllById(copyIdList);
        List<Long> bookIdList = OrdersUtil.getBookIdFromBookCopyList(copyBookList);
        List<BookCatalog> bookCatalogList = bookCatalogService.findAllByIdIn(bookIdList);

        return CreateEntityUtil.createArchiveBookModelEntityList(copyBookList, bookCatalogList, user);
    }

    public List<ArchiveBookModel> getListOfAllArchiveUsage() {
        List<ArchiveBookUsage> allUsageArchive = usageService.findAllUsageArchive();
        List<Long> copyIdFromArchiveUsageList = getBookCopyIdFromArchiveUsageList(allUsageArchive);
        List<CopiesOfBooks> copiesOfBooks = copiesOfBooksService.findAllById(copyIdFromArchiveUsageList);
        List<Long> bookIdList = OrdersUtil.getBookIdFromBookCopyList(copiesOfBooks);
        List<BookCatalog> bookCatalogList = bookCatalogService.findAllByIdIn(bookIdList);
        return getArchiveBookModelList(allUsageArchive, copiesOfBooks, bookCatalogList);
    }

    public List<CopiesOfBooks> unusableConditionBooksList() {
        return copiesOfBooksService.getAllCopyByCondition("unusable");
    }

    public void cancelOrder(Long idCopy, String nickName) {
        UserEntity user = userService.getUserByNickName(nickName);
        orderBookService.cancelOrderLibrarian(idCopy, user);
    }

    public void deleteUnusableBookCopy(Long idCopy) {
        copiesOfBooksService.deleteUnusableBookCopy(idCopy);
    }

    private List<OnlineOrderModel> prepareListOfCompletedOrders(List<CompletedOrders> completedOrders) {
        List<Long> ordersList = getBookCopyIdFromCompletedOrdersList(completedOrders);
        List<CopiesOfBooks> copyBookList = copiesOfBooksService.findAllById(ordersList);
        List<Long> bookIdList = OrdersUtil.getBookIdFromBookCopyList(copyBookList);
        List<BookCatalog> bookCatalogList = bookCatalogService.findAllByIdIn(bookIdList);

        return CreateEntityUtil
                .createCompletedOrdersEntityList(copyBookList, bookCatalogList, completedOrders);
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
    public void returnBookToTheCatalog(ReturnOrderInCatalogModel model) {
        Long readerId = model.getIdReader();
        Long copyId = model.getIdCopy();
        String condition = model.getCondition();
        usageService.putBookInUsageArchive(copyId, readerId, condition);
        completedOrdersService.deleteFromCompletedOrdersByCopyId(copyId);
        copiesOfBooksService.updateCopyOfBookInfo(copyId, condition);
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