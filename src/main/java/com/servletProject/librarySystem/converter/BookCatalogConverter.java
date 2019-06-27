package com.servletProject.librarySystem.converter;

import com.servletProject.librarySystem.domen.BookCatalog;
import com.servletProject.librarySystem.domen.dto.bookCatalog.CreateBookCatalogModel;

public class BookCatalogConverter {

    public static BookCatalog toEntity(CreateBookCatalogModel model) {
        BookCatalog entity = new BookCatalog();
        entity.setBookTitle(model.getBookTitle());
        entity.setBookAuthor(model.getBookAuthor());
        entity.setGenre(model.getGenre());
        entity.setYearOfPublication(model.getYearOfPublication());
        entity.setTotalAmount(1);

        return entity;
    }
}
