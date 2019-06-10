package com.servletProject.librarySystem.domen;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CompletedOrders {
    private long id;
    private long idReader;
    private long idLibrarian;
    private long idBook;
}

