package com.servletProject.librarySystem.repository;

import com.servletProject.librarySystem.domen.OnlineOrderBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

public interface OnlineOrderBookRepository extends JpaRepository<OnlineOrderBook, Long> {

    List<OnlineOrderBook> findAllByIdUser(Long idUser);

    Optional<OnlineOrderBook> findOneByIdUserAndIdBookCopy(Long idUser, Long idCopy);

    @Modifying
    void removeByIdBookCopyAndIdUser(Long idCopy, Long idUser);

    Optional<OnlineOrderBook> findOneByIdBookCopy(Long idCopy);
}
