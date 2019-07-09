package com.servletProject.librarySystem.service.data;

import com.servletProject.librarySystem.domen.OnlineOrderBook;
import com.servletProject.librarySystem.domen.Role;
import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.exception.OrderNotExistException;
import com.servletProject.librarySystem.exception.PermissionToActionIsAbsentException;
import com.servletProject.librarySystem.exception.ThereAreNoBooksFoundException;
import com.servletProject.librarySystem.repository.OnlineOrderBookRepository;
import com.servletProject.librarySystem.utils.CreateEntityUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OnlineOrderBookService {

    private final OnlineOrderBookRepository orderBookRepository;

    public OnlineOrderBookService(OnlineOrderBookRepository orderBookRepository) {
        this.orderBookRepository = orderBookRepository;
    }

    public void reserveBookCopy(Long copyId, Long readerId) {
        orderBookRepository.save(CreateEntityUtil.createOnlineOrderEntity(copyId, readerId));
    }

    public List<OnlineOrderBook> findAllByUserId(Long idUser) {
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

    public void deleteOrderByCopyIdAndUserId(Long bookCopyId, Long userId) {
        if (isOrderExist(bookCopyId, userId)) {
            orderBookRepository.removeByIdBookCopyAndIdUser(bookCopyId, userId);
        } else throw new OrderNotExistException("The order you are looking for does not exist.");
    }

    private boolean isOrderExist(Long bookCopyId, Long userId) {
        Optional<OnlineOrderBook> orderBook = orderBookRepository.findOneByIdUserAndIdBookCopy(userId, bookCopyId);
        return orderBook.isPresent();
    }

    public void cancelOrderByReader(Long idCopy, UserEntity user) {
        Optional<OnlineOrderBook> orderBook = orderBookRepository.findOneByIdBookCopy(idCopy);
        if (orderBook.isPresent()) {
            OnlineOrderBook onlineOrderBook = orderBook.get();
            if (idCopy == onlineOrderBook.getIdBookCopy() && onlineOrderBook.getIdUser() == user.getId()) {
                orderBookRepository.delete(onlineOrderBook);
            } else throw new PermissionToActionIsAbsentException("You do not have permission to delete this order.");
        } else throw new OrderNotExistException("The order you are looking for does not exist.");
    }

    public void cancelOrderLibrarian(Long idCopy, Long idUser) {
        Optional<OnlineOrderBook> orderBook = orderBookRepository.findOneByIdBookCopy(idCopy);
        if (orderBook.isPresent()) {
            OnlineOrderBook onlineOrderBook = orderBook.get();
            if (idUser == onlineOrderBook.getIdUser()) {

                orderBookRepository.delete(onlineOrderBook);
            }
        } else throw new OrderNotExistException("The order you are looking for does not exist.");
    }
}
