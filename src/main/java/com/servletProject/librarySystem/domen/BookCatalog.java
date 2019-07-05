package com.servletProject.librarySystem.domen;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Table(name = "book_catalog")
public class BookCatalog {
    @Column(name = "id", updatable = false, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_book_id")
    private long id;

    @Column(name = "book_title")
    @NotNull
    @NotEmpty(message = "Book title cannot be empty")
    private String bookTitle;

    @Column(name = "book_author")
    @NotNull
    @NotEmpty(message = "Book author name cannot be empty")
    private String bookAuthor;

    @Column(name = "publication")
    @NotNull
    @NotEmpty(message = "Year of book publication cannot be empty")
    private int yearOfPublication;

    @Column(name = "genre")
    @NotNull
    @NotEmpty(message = "Enter book genre")
    private String genre;

    @Column(name = "total_amount")
    private int totalAmount;
}