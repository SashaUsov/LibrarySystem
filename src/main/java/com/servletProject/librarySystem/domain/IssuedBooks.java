package com.servletProject.librarySystem.domain;

import com.sun.istack.internal.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class IssuedBooks {
    private long id;
    @NotNull
    private long idReader;
    @NotNull
    private long idLibrarian;
    @NotNull
    private long idBook;
    @NotNull
    private String readingPlace;
    @NotNull
    private Date dateOfIssue;
    @NotNull
    private int period;
}
