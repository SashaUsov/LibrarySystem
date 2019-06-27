package com.servletProject.librarySystem.service.data;

import com.servletProject.librarySystem.domen.OnlineOrderBook;
import com.servletProject.librarySystem.exception.ThereAreNoBooksFoundException;
import com.servletProject.librarySystem.repository.OnlineOrderBookRepository;
import com.servletProject.librarySystem.utils.CreateEntityUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OnlineOrderBookService {

    private final OnlineOrderBookRepository orderBookRepository;

    public OnlineOrderBookService(OnlineOrderBookRepository orderBookRepository) {
        this.orderBookRepository = orderBookRepository;
    }

    public void reserveBookCopy(Long copyId, Long readerId) {
        orderBookRepository.save(CreateEntityUtil.createOnlineOrderEntity(copyId, readerId));
    }

    public List<OnlineOrderBook> findAllByUserIdIn(Long idUser) {
        List<OnlineOrderBook> orderBookList = orderBookRepository.findAllByIdUser(idUser);
        if (orderBookList != null && !orderBookList.isEmpty()) {
            return orderBookList;
        } else throw new ThereAreNoBooksFoundException("We could not find any order");
    }

    public List<OnlineOrderBook> findAllOrders() {
        List<OnlineOrderBook> orderBookList = orderBookRepository.findAll();
        if (!orderBookList.isEmpty()) {
            return orderBookList;
        } else throw new ThereAreNoBooksFoundException("We could not find any order");
    }
}