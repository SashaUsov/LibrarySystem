package com.servletProject.librarySystem.domen.dto.onlineOrderBook;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OnlineOrderBookModel {

    private Long uniqueId;
    private Long userId;
    private String bookTitle;
    private String bookAuthor;
    private int yearOfPublication;
    private String genre;
}
