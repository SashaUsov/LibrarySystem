package com.servletProject.librarySystem.domen;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "completed_orders")
public class CompletedOrders {

    @Column(name = "id", updatable = false, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_completed_orders_id")
    private long id;

    @Column(name = "id_reader")
    private long idReader;

    @Column(name = "id_librarian")
    private long idLibrarian;

    @Column(name = "id_book")
    private long idBook;
}

