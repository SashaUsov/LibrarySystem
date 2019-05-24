package com.servletProject.librarySystem.domain;

import com.sun.istack.internal.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ArchiveBookUsage {
    @NotNull
    private long id;
    @NotNull
    private long idReader;
    @NotNull
    private long idCopiesBook;
    @NotNull
    private String bookCondition;
    @NotNull
    private int monetarySanctions;
    private boolean finePaid;
}
