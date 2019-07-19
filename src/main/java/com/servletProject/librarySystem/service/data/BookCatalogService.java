package com.servletProject.librarySystem.service.data;

import com.servletProject.librarySystem.converter.BookCatalogConverter;
import com.servletProject.librarySystem.domen.BookCatalog;
import com.servletProject.librarySystem.domen.CopiesOfBooks;
import com.servletProject.librarySystem.domen.dto.bookCatalog.CreateBookCatalogModel;
import com.servletProject.librarySystem.exception.ThereAreNoBooksFoundException;
import com.servletProject.librarySystem.repository.BookRepository;
import com.servletProject.librarySystem.repository.CopiesOfBooksRepository;
import com.servletProject.librarySystem.utils.CreateEntityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        String bookTitle = model.getBookTitle().trim();
        Optional<BookCatalog> entity = bookRepository.findOneByBookTitle(bookTitle);
        if (!entity.isPresent()) {
            saveOriginalBook(model);
        } else {
            BookCatalog bookCatalog = entity.get();
            saveBookCopy(bookCatalog);
        }
    }

    private void saveOriginalBook(CreateBookCatalogModel model) {
        BookCatalog book = bookRepository.save(BookCatalogConverter.toEntity(model));
        log.info("Book with id=" + book.getId() + " added to catalog");
        CopiesOfBooks copiesOfBooks = copiesOfBooksRepository
                .save(CreateEntityUtil.createCopiesOfBooksEntity(book));
        log.info("Book copy with id=" + copiesOfBooks.getId() + " added to catalog");
    }

    private void saveBookCopy(BookCatalog bookCatalog) {
        CopiesOfBooks copiesOfBooks = copiesOfBooksRepository
                .save(CreateEntityUtil.createCopiesOfBooksEntity(bookCatalog));
        log.info("Book copy with id=" + copiesOfBooks.getId() + " added to catalog");
        bookRepository.incrementBookTotalAmount(bookCatalog.getId());
        log.info("Incrementing the total number of copies of the book with id="
                + bookCatalog.getId() + " in the catalog");
    }

    public List<BookCatalog> getAllBook() {
        List<BookCatalog> catalog = bookRepository.findAll();
        if (!catalog.isEmpty()) {
            return catalog;
        } else throw new ThereAreNoBooksFoundException("No books found");
    }

    public List<BookCatalog> getAllBookByTitle(String title){
        List<BookCatalog> book = bookRepository.findAllByBookTitleContaining(title);
        if (!book.isEmpty()) {
            return book;
        } else throw new ThereAreNoBooksFoundException("Books with this name are not in the catalog.");
    }

    public List<BookCatalog> getAllBookByAuthor(String author){
        List<BookCatalog> book = bookRepository.findAllByBookAuthorContaining(author);
        if (!book.isEmpty()) {
            return book;
        } else throw new ThereAreNoBooksFoundException("Books by this author are not in the catalog.");
    }

    public List<BookCatalog> getAllBookByGenre(String genre){
        List<BookCatalog> book = bookRepository.findAllByGenreContaining(genre);
        if (!book.isEmpty()) {
            return book;
        } else throw new ThereAreNoBooksFoundException("No books found");
    }

    public List<BookCatalog> findAllByIdIn(List<Long> idList) {
        List<BookCatalog> catalogList = bookRepository.findAllByIdIn(idList);
        if (!catalogList.isEmpty()) {
            return catalogList;
        } else throw new ThereAreNoBooksFoundException("No books found");
    }
}
