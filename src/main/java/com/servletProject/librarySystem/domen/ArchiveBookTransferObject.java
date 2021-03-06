package com.servletProject.librarySystem.domen;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArchiveBookTransferObject {
    private Long uniqueId;
    private Long userId;
    private String bookTitle;
    private String bookAuthor;
    private int yearOfPublication;
    private String genre;
    private String name;
}
