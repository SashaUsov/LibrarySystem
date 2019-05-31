package com.servletProject.librarySystem.domen;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CopiesOfBooks {
    private long id;
    private long idBook;
    private boolean availability;
    private String bookCondition;
}
