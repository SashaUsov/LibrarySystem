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

    @Modifying
    @Query(value = "UPDATE copies_of_books SET availability= :av WHERE id= :id_new",
    nativeQuery = true)
    void updateAvailabilityById(@Param("id_new") Long idCopy, @Param("av") boolean availability);

    Optional<CopiesOfBooks> findOneByIdAndAndAvailabilityTrue(Long idCopy);

    Optional<CopiesOfBooks> findOneByIdAndAndAvailabilityFalse(Long idCopy);

    List<CopiesOfBooks> findAllByIdIn(Collection<Long> idCopyList);

    @Modifying
    @Query(value = "UPDATE copies_of_books SET availability= :availability, book_condition= :condition WHERE id= :id",
    nativeQuery = true)
    void updateAvailabilityAndConditionOfCopy(@Param("id") Long copyId,
                                              @Param("condition") String condition,
                                              @Param("availability") boolean availability);
}
