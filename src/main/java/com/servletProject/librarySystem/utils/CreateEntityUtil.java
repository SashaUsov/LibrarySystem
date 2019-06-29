package com.servletProject.librarySystem.utils;

import com.servletProject.librarySystem.domen.BookCatalog;
import com.servletProject.librarySystem.domen.CompletedOrders;
import com.servletProject.librarySystem.domen.CopiesOfBooks;
import com.servletProject.librarySystem.domen.OnlineOrderBook;
import com.servletProject.librarySystem.domen.dto.onlineOrderBook.OnlineOrderModel;
import com.servletProject.librarySystem.exception.ThereAreNoBooksFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CreateEntityUtil {

    public static CopiesOfBooks createCopiesOfBooksEntity(BookCatalog book) {
        CopiesOfBooks entity = new CopiesOfBooks();
        entity.setIdBook(book.getId());
        entity.setBookCondition("good");
        entity.setAvailability(true);

        return entity;
    }

    public static OnlineOrderBook createOnlineOrderEntity(Long copyId, Long readerId) {
        OnlineOrderBook orderBook = new OnlineOrderBook();
        orderBook.setIdBookCopy(copyId);
        orderBook.setIdUser(readerId);

        return orderBook;
    }

    public static List<OnlineOrderModel> createOnlineOrderModelEntityList(List<CopiesOfBooks> bookCopyList,
                                                                          List<BookCatalog> bookCatalogList,
                                                                          List<OnlineOrderBook> orderBookList
    ) {
        List<OnlineOrderModel> entityList = new ArrayList<>();
        for (BookCatalog book : bookCatalogList) {
            OnlineOrderModel entity = new OnlineOrderModel();
            createOnlineOrderModelEntity(bookCopyList, orderBookList, book, entity);
            entityList.add(entity);
        }
        return entityList;
    }

    public static List<OnlineOrderModel> createCompletedOrdersEntityList(List<CopiesOfBooks> bookCopyList,
                                                                         List<BookCatalog> bookCatalogList,
                                                                         List<CompletedOrders> completedOrdersList
    ) {
        List<OnlineOrderModel> entityList = new ArrayList<>();
        for (BookCatalog book : bookCatalogList) {
            OnlineOrderModel entity = new OnlineOrderModel();
            createModelEntityFromCompletedOrders(bookCopyList, completedOrdersList, book, entity);
            entityList.add(entity);
        }
        return entityList;
    }

    private static void createModelEntityFromCompletedOrders(List<CopiesOfBooks> bookCopyList,
                                                             List<CompletedOrders> completedOrdersList,
                                                             BookCatalog book,
                                                             OnlineOrderModel entity) {
        entity.setBookTitle(book.getBookTitle());
        entity.setBookAuthor(book.getBookAuthor());
        entity.setGenre(book.getGenre());
        entity.setYearOfPublication(book.getYearOfPublication());

        Optional<CopiesOfBooks> bookCopy = bookCopyList.stream()
                .filter(copiesOfBooks -> book.getId() == copiesOfBooks.getIdBook())
                .findFirst();
        CopiesOfBooks copy = bookCopy.orElseThrow(() -> new ThereAreNoBooksFoundException("We could not find any copies of the book"));
        entity.setUniqueId(copy.getId());

        Optional<CompletedOrders> order = completedOrdersList.stream()
                .filter(o -> copy.getId() == o.getIdBook())
                .findFirst();
        CompletedOrders completedOrders = order.orElseThrow(() -> new ThereAreNoBooksFoundException("We could not find any copies of the book"));
        entity.setUserId(completedOrders.getIdReader());
    }

    private static void createOnlineOrderModelEntity(List<CopiesOfBooks> bookCopyList,
                                                     List<OnlineOrderBook> orderBookList,
                                                     BookCatalog book,
                                                     OnlineOrderModel entity
    ) {
        entity.setBookTitle(book.getBookTitle());
        entity.setBookAuthor(book.getBookAuthor());
        entity.setGenre(book.getGenre());
        entity.setYearOfPublication(book.getYearOfPublication());

        Optional<CopiesOfBooks> bookCopy = bookCopyList.stream()
                .filter(copiesOfBooks -> book.getId() == copiesOfBooks.getIdBook())
                .findFirst();
        CopiesOfBooks copy = bookCopy.orElseThrow(() -> new ThereAreNoBooksFoundException("We could not find any copies of the book"));
        entity.setUniqueId(copy.getId());

        Optional<OnlineOrderBook> order = orderBookList.stream()
                .filter(o -> copy.getId() == o.getIdBookCopy())
                .findFirst();
        OnlineOrderBook onlineOrderBook = order.orElseThrow(() -> new ThereAreNoBooksFoundException("We could not find any copies of the book"));
        entity.setUserId(onlineOrderBook.getIdUser());
    }

    public static CompletedOrders createCompletedOrdersEntity(Long idUser, Long idLibrarian, Long idCopy) {
        CompletedOrders entity = new CompletedOrders();
        entity.setIdReader(idUser);
        entity.setIdLibrarian(idLibrarian);
        entity.setIdBook(idCopy);

        return entity;
    }
}
