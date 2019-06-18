package com.servletProject.librarySystem.domen;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserOrdersTransferObject {
    private Long uniqueId;
    private String bookTitle;
    private String bookAuthor;
    private int yearOfPublication;
    private String genre;
}
