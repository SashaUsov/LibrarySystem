package com.servletProject.librarySystem.domen.dto.onlineOrderBook;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnlineOrderModel {

    private Long uniqueId;
    private Long userId;
    private String bookTitle;
    private String bookAuthor;
    private int yearOfPublication;
    private String genre;
}
