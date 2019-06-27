package com.servletProject.librarySystem.service.data;

import com.servletProject.librarySystem.converter.BookCatalogConverter;
import com.servletProject.librarySystem.domen.BookCatalog;
import com.servletProject.librarySystem.domen.dto.bookCatalog.CreateBookCatalogModel;
import com.servletProject.librarySystem.exception.ThereAreNoBooksFoundException;
import com.servletProject.librarySystem.repository.BookRepository;
import com.servletProject.librarySystem.repository.CopiesOfBooksRepository;
import com.servletProject.librarySystem.utils.copiesOfBooksUtil.CreateCopiesOfBooksEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookCatalogService {
    private final BookRepository bookRepository;
    private final CopiesOfBooksRepository copiesOfBooksRepository;

    public BookCatalogService(BookRepository bookRepository, CopiesOfBooksRepository copiesOfBooksRepository) {
        this.bookRepository = bookRepository;
        this.copiesOfBooksRepository = copiesOfBooksRepository;
    }

    @Transactional
    public void saveBook(CreateBookCatalogModel model) {
        String bookTitle = model.getBookTitle().trim();
        Optional<BookCatalog> entity = bookRepository.findOneByBookTitle(bookTitle);
        if (!entity.isPresent()) {
            BookCatalog book = bookRepository.save(BookCatalogConverter.toEntity(model));
            copiesOfBooksRepository.save(CreateCopiesOfBooksEntity.createEntity(book));
        } else {
            final BookCatalog bookCatalog = entity.get();
            copiesOfBooksRepository.save(CreateCopiesOfBooksEntity.createEntity(bookCatalog));
            bookRepository.incrementBookTotalAmount(bookCatalog.getId());
        }
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
        } else throw new ThereAreNoBooksFoundException("No books found");
    }

    public List<BookCatalog> getAllBookByAuthor(String author){
        List<BookCatalog> book = bookRepository.findAllByBookAuthorContaining(author);
        if (!book.isEmpty()) {
            return book;
        } else throw new ThereAreNoBooksFoundException("No books found");
    }

    public List<BookCatalog> getAllBookByGenre(String genre){
        List<BookCatalog> book = bookRepository.findAllByGenreContaining(genre);
        if (!book.isEmpty()) {
            return book;
        } else throw new ThereAreNoBooksFoundException("No books found");
    }
}