package com.servletProject.librarySystem.repository;

import com.servletProject.librarySystem.domen.CopiesOfBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CopiesOfBooksRepository extends JpaRepository<CopiesOfBooks, Long> {

    List<CopiesOfBooks> findAllByIdBook(Long idBook);

    List<CopiesOfBooks> findAllByBookConditionAndAvailabilityTrue(String condition);

    Long findIdBookById(Long idCopy);

    @Modifying
    @Query(value = "UPDATE copies_of_books cb SET cb.availability=: availability WHERE cb.id=: id",
    nativeQuery = true)
    void updateAvailabilityById(@Param("id") Long idCopy, @Param("availability") boolean availability);

    Optional<CopiesOfBooks> findOneByIdAndAAndAvailabilityTrue(Long idCopy);

    List<CopiesOfBooks> findAllByIdIn(Collection<Long> idCopyList);

    @Modifying
    @Query(value = "UPDATE copies_of_books cb SET cb.availability=: availability, cb.book_condition=: condition WHERE cb.id=: id",
    nativeQuery = true)
    void updateAvailabilityAndConditionOfCopy(@Param("id") Long copyId,
                                              @Param("condition") String condition,
                                              @Param("availability") boolean availability);
}
