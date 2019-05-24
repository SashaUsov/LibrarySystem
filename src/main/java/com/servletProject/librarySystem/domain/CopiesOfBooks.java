package com.servletProject.librarySystem.domain;

import com.sun.istack.internal.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CopiesOfBooks {
    private long id;
    @NotNull
    private long idBook;
    @NotNull
    private boolean availability;
    private String bookCondition;
}
