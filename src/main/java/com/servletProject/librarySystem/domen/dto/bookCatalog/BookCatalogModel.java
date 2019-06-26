package com.servletProject.librarySystem.domen.dto.bookCatalog;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BookCatalogModel {
    private long id;
    private String bookTitle;
    private String bookAuthor;
    private int yearOfPublication;
    private String genre;
    private int totalAmount;
}