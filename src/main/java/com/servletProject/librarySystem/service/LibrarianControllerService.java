package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.domen.*;
import com.servletProject.librarySystem.domen.dto.archiveBookUsage.ArchiveBookModel;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.OnlineOrderModel;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.ReturnOrderInCatalogModel;
import com.servletProject.librarySystem.exception.PermissionToActionIsAbsentException;
import com.servletProject.librarySystem.service.data.*;
import com.servletProject.librarySystem.utils.CreateEntityUtil;
import com.servletProject.librarySystem.utils.OrdersUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
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
    public void giveBookToTheReader(Long idCopy, Long idUser, String librarian) {
        UserEntity user = userService.getUserByNickName(librarian);
        if (user.getRoles().contains(Role.LIBRARIAN)) {
            orderBookService.deleteOrderByCopyIdAndUserId(idCopy, idUser);
            completedOrdersService.saveOrder(idUser, user.getId(), idCopy);
            log.info("The issuance of the book is successful.");
        } else throw new PermissionToActionIsAbsentException("You do not have permission to confirm this order.");
    }

    public List<OnlineOrderModel> getListOfCompletedOrdersByReader(String email, String librarian) {
        UserEntity user = userService.getUserByNickName(librarian);
        if (user.getRoles().contains(Role.LIBRARIAN)) {
            Long idUser = userService.getUserIdByEmail(email);
            List<CompletedOrders> completedOrders = completedOrdersService.findAllByUserId(idUser);
            return prepareListOfCompletedOrders(completedOrders);
        } else throw new PermissionToActionIsAbsentException("You do not have permission.");
    }

    public List<OnlineOrderModel> getListOfCompletedOrdersByUser(String nickName) {
        UserEntity user = userService.getUserByNickName(nickName);
        List<CompletedOrders> completedOrders = completedOrdersService.findAllByUserId(user.getId());
        return prepareListOfCompletedOrders(completedOrders);

    }

    public List<OnlineOrderModel> getListOfAllCompletedOrders(String librarian) {
        UserEntity user = userService.getUserByNickName(librarian);
        if (user.getRoles().contains(Role.LIBRARIAN)) {
            List<CompletedOrders> completedOrders = completedOrdersService.findAllCompletedOrders();
            return prepareListOfCompletedOrders(completedOrders);
        } else throw new PermissionToActionIsAbsentException("You do not have permission.");
    }

    public List<ArchiveBookModel> getListOfActiveUsageByUser(String email, String librarian) {
        UserEntity user = userService.getUserByNickName(librarian);
        if (user.getRoles().contains(Role.LIBRARIAN)) {
            UserEntity entity = userService.getUserByEmail(email);
            List<ArchiveBookUsage> bookUsageList = usageService.findAllByUserId(entity.getId());
            List<Long> copyIdList = getBookCopyIdFromArchiveUsageList(bookUsageList);
            List<CopiesOfBooks> copyBookList = copiesOfBooksService.findAllById(copyIdList);
            List<Long> bookIdList = OrdersUtil.getBookIdFromBookCopyList(copyBookList);
            List<BookCatalog> bookCatalogList = bookCatalogService.findAllByIdIn(bookIdList);

            return CreateEntityUtil.createArchiveBookModelEntityList(copyBookList, bookCatalogList, entity);
        } else throw new PermissionToActionIsAbsentException("You do not have permission.");
    }

    public List<ArchiveBookModel> getListOfAllArchiveUsage(String librarian) {
        UserEntity user = userService.getUserByNickName(librarian);
        if (user.getRoles().contains(Role.LIBRARIAN)) {
            List<ArchiveBookUsage> allUsageArchive = usageService.findAllUsageArchive();
            List<Long> copyIdFromArchiveUsageList = getBookCopyIdFromArchiveUsageList(allUsageArchive);
            List<CopiesOfBooks> copiesOfBooks = copiesOfBooksService.findAllById(copyIdFromArchiveUsageList);
            List<Long> bookIdList = OrdersUtil.getBookIdFromBookCopyList(copiesOfBooks);
            List<BookCatalog> bookCatalogList = bookCatalogService.findAllByIdIn(bookIdList);
            return getArchiveBookModelList(allUsageArchive, copiesOfBooks, bookCatalogList);
        } else throw new PermissionToActionIsAbsentException("You do not have permission.");
    }

    public List<CopiesOfBooks> unusableConditionBooksList(String librarian) {
        UserEntity user = userService.getUserByNickName(librarian);
        if (user.getRoles().contains(Role.LIBRARIAN)) {
            return copiesOfBooksService.getAllCopyByCondition("unusable");
        } else throw new PermissionToActionIsAbsentException("You do not have permission.");
    }

    @Transactional
    public void cancelOrder(Long idCopy, Long idUser, String librarian) {
        UserEntity user = userService.getUserByNickName(librarian);
        if (user.getRoles().contains(Role.LIBRARIAN)) {
            orderBookService.cancelOrderLibrarian(idCopy, idUser);
            copiesOfBooksService.updateAvailabilityOfCopy(true, idCopy);
            log.info("Order canceled successfully by librarian. Librarian id = " + user.getId() + " .");
        } else throw new PermissionToActionIsAbsentException("You do not have permission to delete this order.");
    }

    @Transactional
    public void deleteUnusableBookCopy(Long idCopy, String librarian) {
        UserEntity user = userService.getUserByNickName(librarian);
        if (user.getRoles().contains(Role.LIBRARIAN)) {
            copiesOfBooksService.deleteUnusableBookCopy(idCopy);
            log.info("Unusable book copy successfully deleted by librarian. Librarian id = " + user.getId() + " .");
        } else throw new PermissionToActionIsAbsentException("You do not have permission to remove this book from the catalog.");
    }

    @Transactional
    public void returnBookToTheCatalog(ReturnOrderInCatalogModel model, String librarian) {
        UserEntity user = userService.getUserByNickName(librarian);
        if (user.getRoles().contains(Role.LIBRARIAN)) {
            Long readerId = model.getIdReader();
            Long copyId = model.getIdCopy();
            String condition = model.getCondition();
            usageService.putBookInUsageArchive(readerId, copyId, condition);
            completedOrdersService.deleteFromCompletedOrdersByCopyId(copyId);
            copiesOfBooksService.updateCopyOfBookInfo(copyId, condition);
            log.info("Book copy successfully returned to the catalog.");
        } else throw new PermissionToActionIsAbsentException("You do not have permission to delete this order.");
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
