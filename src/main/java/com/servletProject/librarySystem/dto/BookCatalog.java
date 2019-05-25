package com.servletProject.librarySystem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookCatalog {
    private long id;
    private String bookTitle;
    private String bookAuthor;
    private int yearOfPublication;
    private String genre;
    private int totalAmount;
}