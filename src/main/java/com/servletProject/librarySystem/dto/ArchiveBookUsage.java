package com.servletProject.librarySystem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ArchiveBookUsage {

    private long id;
    private long idReader;
    private long idCopiesBook;
    private String bookCondition;
    private int monetarySanctions;
    private boolean finePaid;
}
