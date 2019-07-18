package com.servletProject.librarySystem.service.data;

import com.servletProject.librarySystem.domen.CopiesOfBooks;
import com.servletProject.librarySystem.exception.ThereAreNoBooksFoundException;
import com.servletProject.librarySystem.repository.ArchiveBookUsageRepository;
import com.servletProject.librarySystem.repository.BookRepository;
import com.servletProject.librarySystem.repository.CopiesOfBooksRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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

    @Transactional
    public void deleteUnusableBookCopy(Long copyId){
        Optional<CopiesOfBooks> copiesOfBooks = copiesOfBooksRepository.findOneByIdAndAndAvailabilityTrue(copyId);
        if(copiesOfBooks.isPresent()) {
            deleteIfCopyPresent(copiesOfBooks.get());
            log.info("Book info from id=" + copyId + " deleted completely");
        } else {
            throw new ThereAreNoBooksFoundException("We could not find any copies of the book");
        }
    }

    public boolean ifPresent(Long idCopy) {
        Optional<CopiesOfBooks> copiesOfBooks = copiesOfBooksRepository.findOneByIdAndAndAvailabilityFalse(idCopy);
        log.info("Book copy with id=" + idCopy + " not found in catalog");
        return copiesOfBooks.isPresent();
    }

    public void updateAvailabilityOfCopy(boolean availability, Long copyId){
        copiesOfBooksRepository.updateAvailabilityById(copyId, availability);
        log.info("Updated book availability with id=" + copyId + ". available -" + availability);
    }

    public List<CopiesOfBooks> findAllById(List<Long> copyIdList) {
        List<CopiesOfBooks> copiesOfBooksList = copiesOfBooksRepository.findAllByIdIn(copyIdList);
        if (copiesOfBooksList != null && !copiesOfBooksList.isEmpty()) {
            return copiesOfBooksList;
        } else throw new ThereAreNoBooksFoundException("We could not find any copies of the book");
    }

    public List<CopiesOfBooks> findAllCopy(Long copyId) {
        List<CopiesOfBooks> copiesOfBooksList = copiesOfBooksRepository.findAllByIdBook(copyId);
        if (copiesOfBooksList != null && !copiesOfBooksList.isEmpty()) {
            return copiesOfBooksList;
        } else throw new ThereAreNoBooksFoundException("We could not find any copies of the book");
    }

    private void deleteIfCopyPresent(CopiesOfBooks copiesOfBooks) {
        archiveBookUsageRepository.deleteAllByIdCopiesBook(copiesOfBooks.getId());
        log.info("Information about the use of the book with ID=" + copiesOfBooks.getId() + " removed from the archive");
        bookRepository.decrementBookTotalAmount(copiesOfBooks.getIdBook());
        log.info("Decrement total amount book with id=" + copiesOfBooks.getIdBook());
        copiesOfBooksRepository.delete(copiesOfBooks);
        log.info("Copy instance with id=" + copiesOfBooks.getId() + " removed from catalog");
    }

    public void updateCopyOfBookInfo(Long copyId, String condition) {
        if (ifPresent(copyId)) {
            boolean availability = isAvailability(condition);
            copiesOfBooksRepository.updateAvailabilityAndConditionOfCopy(copyId, condition, availability);
            log.info("Update status book copy with id=" + copyId + ". Condition: " + condition + " availability: " + availability);
        } else throw new ThereAreNoBooksFoundException("Unable to update book status because the book does not exist");
    }

    public List<CopiesOfBooks> getAllCopyByCondition(String condition) {
        List<CopiesOfBooks> copiesOfBooks = copiesOfBooksRepository.findAllByBookConditionAndAvailabilityTrue(condition);
        if (copiesOfBooks != null && !copiesOfBooks.isEmpty()) {
            return copiesOfBooks;
        } else throw new ThereAreNoBooksFoundException("No damaged books found.");
    }

    private boolean isAvailability(String condition) {
        boolean b = true;
        if ("unusable".equalsIgnoreCase(condition)) b = false;
        return b;
    }
}
