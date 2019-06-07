package com.servletProject.librarySystem.utils;

import com.servletProject.librarySystem.domen.BookCatalog;
import com.servletProject.librarySystem.domen.UserEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class DomainModelUtil {

    public static UserEntity createUserEntity(ResultSet resultSet) throws SQLException {

        UserEntity userFromDb = new UserEntity();

        userFromDb.setId(resultSet.getLong("id"));
        userFromDb.setFirstName(resultSet.getString("first_name"));
        userFromDb.setLastName(resultSet.getString("last_name"));
        userFromDb.setNickName(resultSet.getString("nick_name"));
        userFromDb.setMail(resultSet.getString("mail"));
        userFromDb.setAddress(resultSet.getString("address"));
        userFromDb.setPermissionToOrder(resultSet.getBoolean("permission_to_order"));

        return userFromDb;
    }

    public static BookCatalog createBookFromMap(Map<String, String> book) {
        BookCatalog bookCatalog= new BookCatalog();
        bookCatalog.setId(Long.valueOf(book.get("id")));
        bookCatalog.setBookAuthor(book.get("bookAuthor"));
        bookCatalog.setBookTitle(book.get("bookTitle"));
        bookCatalog.setGenre(book.get("genre"));
        bookCatalog.setYearOfPublication(Integer.valueOf(book.get("yearOfPublication")));
        bookCatalog.setTotalAmount(Integer.valueOf(book.get("totalAmount")));

        return bookCatalog;
    }
}
