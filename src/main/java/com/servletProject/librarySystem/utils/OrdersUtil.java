package com.servletProject.librarySystem.utils;

import com.servletProject.librarySystem.domen.CopiesOfBooks;
import com.servletProject.librarySystem.domen.OnlineOrderBook;

import java.util.List;
import java.util.stream.Collectors;

public class OrdersUtil {

    public static List<Long> getBookCopyIdFromOnlineOrderList(List<OnlineOrderBook> orderBookList) {
        return orderBookList.stream()
                .map(OnlineOrderBook::getIdBookCopy)
                .collect(Collectors.toList());
    }

    public static List<Long> getBookIdFromBookCopyList(List<CopiesOfBooks> copyBookList) {
        return copyBookList.stream()
                .map(CopiesOfBooks::getIdBook)
                .collect(Collectors.toList());
    }
}
