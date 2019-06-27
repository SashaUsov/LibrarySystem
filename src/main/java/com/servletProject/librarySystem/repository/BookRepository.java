package com.servletProject.librarySystem.repository;

import com.servletProject.librarySystem.domen.BookCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookCatalog, Long> {

    Optional<BookCatalog> findOneByBookTitle(String bookTitle);

    List<BookCatalog> findAllByBookTitleContaining(String title);

    List<BookCatalog> findAllByBookAuthorContaining(String author);

    List<BookCatalog> findAllByGenreContaining(String genre);

    @Modifying
    @Query(value = "UPDATE book_catalog bc SET bc.total_amount= bc.total_amount-1 WHERE bc.id=: id",
    nativeQuery = true)
    void decrementBookTotalAmount(@Param("id") Long id);

    @Modifying
    @Query(value = "UPDATE book_catalog bc SET bc.total_amount= bc.total_amount+1 WHERE bc.id=: id",
            nativeQuery = true)
    void incrementBookTotalAmount(@Param("id") Long id);

    List<BookCatalog> findAllByIdIn(Collection<Long> id);
}
