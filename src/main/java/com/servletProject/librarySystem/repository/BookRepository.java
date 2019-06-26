package com.servletProject.librarySystem.repository;

import com.servletProject.librarySystem.domen.BookCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface BookRepository extends JpaRepository<BookCatalog, Long> {

    Long findIdByBookTitle(String bookTitle);

    BookCatalog findOneByBookTitleContaining(String title);

    BookCatalog findOneByBookAuthorContaining(String author);

    BookCatalog findOneByGenreContaining(String genre);

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
