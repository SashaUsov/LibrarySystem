package com.servletProject.librarySystem.domen.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ArchiveBookTransferObject {

    @NotNull
    @NotEmpty
    private Long uniqueId;

    @NotNull
    @NotEmpty
    private Long userId;

    @NotNull
    @NotEmpty
    private String bookTitle;

    @NotNull
    @NotEmpty
    private String bookAuthor;

    @NotNull
    @NotEmpty
    private int yearOfPublication;

    @NotNull
    @NotEmpty
    private String genre;

    @NotNull
    @NotEmpty
    private String name;
}
