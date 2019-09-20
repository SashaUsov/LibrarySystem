package com.servletProject.librarySystem.domen;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "book_order")
public class OnlineOrderBook {
    @Column(name = "id", updatable = false, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_book_order_id")
    private long id;

    @Column(name = "user_id")
    private long idUser;

    @Column(name = "book_copy_id")
    private long idBookCopy;
}
