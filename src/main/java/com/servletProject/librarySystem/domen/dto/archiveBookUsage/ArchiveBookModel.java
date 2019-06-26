package com.servletProject.librarySystem.domen.dto.archiveBookUsage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArchiveBookModel {

    private Long uniqueId;
    private Long userId;
    private String bookTitle;
    private String bookAuthor;
    private int yearOfPublication;
    private String genre;
    private String name;
}
