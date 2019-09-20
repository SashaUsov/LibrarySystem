package com.servletProject.librarySystem.domen;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "copies_of_books")
public class CopiesOfBooks {
    @Column(name = "id", updatable = false, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_book_copy_id")
    private long id;

    @Column(name = "id_book")
    private long idBook;

    @Column(name = "availability")
    private boolean availability;

    @Column(name = "book_condition")
    private String bookCondition;
}
