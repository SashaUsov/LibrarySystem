package com.servletProject.librarySystem.domain;

import com.sun.istack.internal.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookCatalog {
    private long id;
    @NotNull
    private String bookTitle;
    @NotNull
    private String bookAuthor;
    @NotNull
    private int yearOfPublication;
    private String genre;
    @NotNull
    private int totalAmount;
}
