package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.domen.CopiesOfBooks;
import com.servletProject.librarySystem.exception.ThereAreNoBooksFoundException;
import com.servletProject.librarySystem.repository.ArchiveBookUsageRepository;
import com.servletProject.librarySystem.repository.BookRepository;
import com.servletProject.librarySystem.repository.CopiesOfBooksRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class CopiesOfBooksService {

    private final BookRepository bookRepository;
    private final CopiesOfBooksRepository copiesOfBooksRepository;
    private final ArchiveBookUsageRepository archiveBookUsageRepository;

    public CopiesOfBooksService(BookRepository bookRepository,
                                CopiesOfBooksRepository copiesOfBooksRepository,
                                ArchiveBookUsageRepository archiveBookUsageRepository
    ) {
        this.bookRepository = bookRepository;
        this.copiesOfBooksRepository = copiesOfBooksRepository;
        this.archiveBookUsageRepository = archiveBookUsageRepository;
    }

    public List<CopiesOfBooks> getAllBookCopy(Long bookId){
        List<CopiesOfBooks> copiesOfBooksList = copiesOfBooksRepository.findAllByIdBook(bookId);
        if (!copiesOfBooksList.isEmpty()) {
            return copiesOfBooksList;
        } else {
            throw new ThereAreNoBooksFoundException("We could not find any copies of the book");
//            log.error("", );
        }
    }

    @Transactional
    public void deleteUnusableBookCopy(Long copyId){
        Optional<CopiesOfBooks> copiesOfBooks = copiesOfBooksRepository.findOneByIdAndAAndAvailabilityTrue(copyId);
        if(copiesOfBooks.isPresent()) {
            ifCopyPresent(copiesOfBooks.get());
        } else {
            throw new ThereAreNoBooksFoundException("We could not find any copies of the book");
        }
    }

    private void ifCopyPresent(CopiesOfBooks copiesOfBooks) {
        archiveBookUsageRepository.deleteAllByIdCopiesBook(copiesOfBooks.getId());
        Long idBook = copiesOfBooksRepository.findIdBookById(copiesOfBooks.getId());
        copiesOfBooksRepository.delete(copiesOfBooks);
        bookRepository.decrementBookTotalAmount(idBook);
    }
}
