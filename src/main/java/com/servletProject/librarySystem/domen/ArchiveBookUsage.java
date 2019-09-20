package com.servletProject.librarySystem.domen;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "archive_book_usage")
public class ArchiveBookUsage {

    @Column(name = "id", updatable = false, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_archive_book_usage_id")
    private long id;

    @Column(name = "id_reader")
    private long idReader;

    @Column(name = "id_copies_book")
    private long idCopiesBook;

    @Column(name = "book_condition")
    private String bookCondition;

}
