package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.dao.BookingDao;
import com.servletProject.librarySystem.dao.BooksDao;
import com.servletProject.librarySystem.dao.transactionManager.TransactionManager;
import com.servletProject.librarySystem.domen.BookCatalog;
import com.servletProject.librarySystem.domen.CopiesOfBooks;
import com.servletProject.librarySystem.utils.DomainModelUtil;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BooksService {
    private final BooksDao booksDao = new BooksDao();
    private final BookingDao bookingDao = new BookingDao();

    public void saveBook(Map<String, String> paramMap) throws SQLException {
        try {
            TransactionManager.beginTransaction();
            Long bookId= booksDao.findBookIdByBookTitle(paramMap.get("book_title"));
            if (bookId == null) {
                booksDao.saveBook(paramMap);
            } else {
                booksDao.saveBookCopy(bookId, true);
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
            List<Map<String, String>> bookCatalog = booksDao.getAllBook();
            return createBookCatalog(catalog, bookCatalog);
        } catch (SQLException | NullPointerException e) {
            TransactionManager.rollBackTransaction();
            throw e;
        } finally {
            TransactionManager.commitTransaction();
        }
    }

    public List<CopiesOfBooks> getAllBookCopy(long bookId) throws SQLException {
        List<CopiesOfBooks> catalog = new ArrayList<>();
        try {
            TransactionManager.beginTransaction();
            List<Map<String, String>> booksCopy = booksDao.getAllBookCopy(bookId);
            return DomainModelUtil.createCopyBookCatalog(catalog, booksCopy);
        } catch (SQLException | NullPointerException e) {
            TransactionManager.rollBackTransaction();
            throw e;
        } finally {
            TransactionManager.commitTransaction();
        }
    }

    public List<BookCatalog> getAllBookByTitle(String title) throws SQLException {
        List<BookCatalog> catalog = new ArrayList<>();
        String bookTitle = "%" + title + "%";
        try {
            TransactionManager.beginTransaction();
            List<Map<String, String>> bookCatalog = booksDao.findBookByTitle(bookTitle);
            return createBookCatalog(catalog, bookCatalog);
        } catch (SQLException | NullPointerException e) {
            TransactionManager.rollBackTransaction();
            throw e;
        } finally {
            TransactionManager.commitTransaction();
        }
    }

    public List<BookCatalog> getAllBookByAuthor(String author) throws SQLException {
        List<BookCatalog> catalog = new ArrayList<>();
        String bookAuthor = "%" + author + "%";
        try {
            TransactionManager.beginTransaction();
            List<Map<String, String>> bookCatalog = booksDao.findBookByAuthor(bookAuthor);
            return createBookCatalog(catalog, bookCatalog);
        } catch (SQLException | NullPointerException e) {
            TransactionManager.rollBackTransaction();
            throw e;
        } finally {
            TransactionManager.commitTransaction();
        }
    }

    public List<BookCatalog> getAllBookByGenre(String genre) throws SQLException {
        List<BookCatalog> catalog = new ArrayList<>();
        String bookGenre = "%" + genre + "%";
        try {
            TransactionManager.beginTransaction();
            List<Map<String, String>> bookCatalog = booksDao.findBookByGenre(bookGenre);
            return createBookCatalog(catalog, bookCatalog);
        } catch (SQLException | NullPointerException e) {
            TransactionManager.rollBackTransaction();
            throw e;
        } finally {
            TransactionManager.commitTransaction();
        }
    }

    public void deleteUnusableBookCopy(Long copyId) throws SQLException {
        try {
            TransactionManager.beginTransaction();
            CopiesOfBooks copyOfBook = bookingDao.findBookCopyByCopyIdAndAvailability(copyId);
            if (copyOfBook == null) {
                throw new SQLException();
            } else {
                booksDao.deleteFromArchive(copyId);
                Long bookIdByBookCopyId = booksDao.findBookIdByBookCopyId(copyId);
                booksDao.deleteCopyById(copyId);
                booksDao.decrementBookTotalAmount(bookIdByBookCopyId);
            }
        } catch (SQLException | NullPointerException e) {
            TransactionManager.rollBackTransaction();
            throw e;
        } finally {
            TransactionManager.commitTransaction();
        }
    }

    private List<BookCatalog> createBookCatalog(List<BookCatalog> catalog, List<Map<String, String>> bookCatalog) throws SQLException {
        if (bookCatalog != null) {
            for (Map<String, String> oneBook : bookCatalog) {
                catalog.add(DomainModelUtil.createBookFromMap(oneBook));
            }
            return catalog;
        } else {
            throw new SQLException("Book catalog is empty :(");
        }
    }
}