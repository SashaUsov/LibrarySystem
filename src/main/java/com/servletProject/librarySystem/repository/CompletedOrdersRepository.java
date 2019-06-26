package com.servletProject.librarySystem.repository;

import com.servletProject.librarySystem.domen.CompletedOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface CompletedOrdersRepository extends JpaRepository<CompletedOrders, Long> {

    List<CompletedOrders> findAllByIdReader(Long idReader);

    @Modifying
    void deleteOneByIdBook(Long idBook);
}
