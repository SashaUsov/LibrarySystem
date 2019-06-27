package com.servletProject.librarySystem.utils.copiesOfBooksUtil;

import com.servletProject.librarySystem.domen.BookCatalog;
import com.servletProject.librarySystem.domen.CopiesOfBooks;
import com.servletProject.librarySystem.domen.dto.bookCatalog.CreateBookCatalogModel;

public class CreateCopiesOfBooksEntity {

    public static CopiesOfBooks createEntity(BookCatalog book) {
        CopiesOfBooks entity = new CopiesOfBooks();
        entity.setIdBook(book.getId());
        entity.setBookCondition("good");
        entity.setAvailability(true);

        return entity;
    }
}
