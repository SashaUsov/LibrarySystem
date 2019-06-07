package com.servletProject.librarySystem.domen;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookCatalog {
    private long id;
    private String bookTitle;
    private String bookAuthor;
    private int yearOfPublication;
    private String genre;
    private int totalAmount;
}