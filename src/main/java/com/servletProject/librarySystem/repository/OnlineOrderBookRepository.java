package com.servletProject.librarySystem.repository;

import com.servletProject.librarySystem.domen.OnlineOrderBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OnlineOrderBookRepository extends JpaRepository<OnlineOrderBook, Long> {

    List<OnlineOrderBook> findAllByIdUser(Long idUser);

    OnlineOrderBook findByIdUserAndIdBookCopy(Long idUser, Long idCopy);
}
