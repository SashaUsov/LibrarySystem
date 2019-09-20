package com.servletProject.librarySystem.service.data;

import com.servletProject.librarySystem.converter.BookCatalogConverter;
import com.servletProject.librarySystem.domen.BookCatalog;
import com.servletProject.librarySystem.domen.dto.bookCatalog.CreateBookCatalogModel;
import com.servletProject.librarySystem.exception.ThereAreNoBooksFoundException;
import com.servletProject.librarySystem.repository.BookRepository;
import com.servletProject.librarySystem.repository.CopiesOfBooksRepository;
import com.servletProject.librarySystem.utils.CreateEntityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookCatalogService {
    private final BookRepository bookRepository;
    private final CopiesOfBooksRepository copiesOfBooksRepository;

    public BookCatalogService(BookRepository bookRepository, CopiesOfBooksRepository copiesOfBooksRepository) {
        this.bookRepository = bookRepository;
        this.copiesOfBooksRepository = copiesOfBooksRepository;
    }

    public void saveBook(CreateBookCatalogModel model) {
        var bookTitle = model.getBookTitle().trim();
        var entity = bookRepository.findOneByBookTitle(bookTitle);
        if (entity.isEmpty()) {
            saveOriginalBook(model);
        } else {
            var bookCatalog = entity.get();
            saveBookCopy(bookCatalog);
        }
    }

    private void saveOriginalBook(CreateBookCatalogModel model) {
        var book = bookRepository.save(BookCatalogConverter.toEntity(model));
        log.info("Book with id=" + book.getId() + " added to catalog");
        var copiesOfBooks = copiesOfBooksRepository
                .save(CreateEntityUtil.createCopiesOfBooksEntity(book));
        log.info("Book copy with id=" + copiesOfBooks.getId() + " added to catalog");
    }

    private void saveBookCopy(BookCatalog bookCatalog) {
        var copiesOfBooks = copiesOfBooksRepository
                .save(CreateEntityUtil.createCopiesOfBooksEntity(bookCatalog));
        log.info("Book copy with id=" + copiesOfBooks.getId() + " added to catalog");
        bookRepository.incrementBookTotalAmount(bookCatalog.getId());
        log.info("Incrementing the total number of copies of the book with id="
                + bookCatalog.getId() + " in the catalog");
    }

    public List<BookCatalog> getAllBook() {
        var catalog = bookRepository.findAll();
        if (!catalog.isEmpty()) {
            return catalog;
        } else throw new ThereAreNoBooksFoundException("No books found");
    }

    public List<BookCatalog> getAllBookByTitle(String title){
        var book = bookRepository.findAllByBookTitleContaining(title);
        if (!book.isEmpty()) {
            return book;
        } else throw new ThereAreNoBooksFoundException("Books with this name are not in the catalog.");
    }

    public List<BookCatalog> getAllBookByAuthor(String author){
        var book = bookRepository.findAllByBookAuthorContaining(author);
        if (!book.isEmpty()) {
            return book;
        } else throw new ThereAreNoBooksFoundException("Books by this author are not in the catalog.");
    }

    public List<BookCatalog> getAllBookByGenre(String genre){
        var book = bookRepository.findAllByGenreContaining(genre);
        if (!book.isEmpty()) {
            return book;
        } else throw new ThereAreNoBooksFoundException("No books found");
    }

    public List<BookCatalog> findAllByIdIn(List<Long> idList) {
        var catalogList = bookRepository.findAllByIdIn(idList);
        if (!catalogList.isEmpty()) {
            return catalogList;
        } else throw new ThereAreNoBooksFoundException("No books found");
    }
}
