package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.dao.LibrarianDao;
import com.servletProject.librarySystem.dao.queries.LibrarianDaoQueries;
import com.servletProject.librarySystem.dao.queries.UserEntityDaoQueries;
import com.servletProject.librarySystem.dao.transaction.TransactionManager;
import com.servletProject.librarySystem.domen.BookCatalog;
import com.servletProject.librarySystem.exception.ClientAlreadyExistsException;
import com.servletProject.librarySystem.utils.DaoUtil;
import com.servletProject.librarySystem.utils.DomainModelUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LibrarianService {
    private final LibrarianDao librarianDao = new LibrarianDao();

    public void saveBook(Map<String, String> paramMap) throws SQLException {
        try {
            TransactionManager.beginTransaction();
            Long bookId= librarianDao.findBookIdByBookTitle(paramMap.get("book_title"));
            if (bookId == null) {
                librarianDao.saveBook(paramMap);
            } else {
                librarianDao.saveBookCopy(bookId, true);
            }
        } catch (SQLException | NullPointerException e) {
            TransactionManager.rollBackTransaction();
            throw e;
        } finally {
            TransactionManager.commitTransaction();
        }
    }

    public List<BookCatalog> getAllBook() throws SQLException {
        List<BookCatalog> catalog = new ArrayList<>();
        try {
            TransactionManager.beginTransaction();
            List<Map<String, String>> bookCatalog = librarianDao.getAllBook();
            if (bookCatalog != null) {
                for (Map<String, String> oneBook : bookCatalog) {
                    catalog.add(DomainModelUtil.createBookFromMap(oneBook));
                }
                return catalog;
            } else {
                throw new SQLException("Book catalog is empty :(");
            }
        } catch (SQLException | NullPointerException e) {
            TransactionManager.rollBackTransaction();
            throw e;
        } finally {
            TransactionManager.commitTransaction();
        }
    }
}
