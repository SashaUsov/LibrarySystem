package com.servletProject.librarySystem.domen.dto.bookCatalog;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreateBookCatalogModel {

    @NotNull
    @NotEmpty(message = "Book title cannot be empty")
    private String bookTitle;

    @NotNull
    @NotEmpty(message = "Book author name cannot be empty")
    private String bookAuthor;

    @NotNull
    private int yearOfPublication;

    @NotNull
    @NotEmpty(message = "Enter book genre")
    private String genre;
}