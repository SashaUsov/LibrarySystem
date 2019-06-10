package com.servletProject.librarySystem.domen;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArchiveBookUsage {

    private long id;
    private long idReader;
    private long idCopiesBook;
    private String bookCondition;

}
