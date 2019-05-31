package com.servletProject.librarySystem.domen;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class IssuedBooks {
    private long id;
    private long idReader;
    private long idLibrarian;
    private long idBook;
    private String readingPlace;
    private Date dateOfIssue;
    private int period;
}

