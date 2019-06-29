package com.servletProject.librarySystem.service.data;

import com.servletProject.librarySystem.domen.CompletedOrders;
import com.servletProject.librarySystem.exception.OrderNotExistException;
import com.servletProject.librarySystem.repository.CompletedOrdersRepository;
import com.servletProject.librarySystem.utils.CreateEntityUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompletedOrdersService {

    private final CompletedOrdersRepository completedOrdersRepository;

    public CompletedOrdersService(CompletedOrdersRepository completedOrdersRepository) {
        this.completedOrdersRepository = completedOrdersRepository;
    }

    public void saveOrder(Long idUser, Long idLibrarian, Long idCopy) {
        CompletedOrders ordersEntity = CreateEntityUtil.createCompletedOrdersEntity(idUser, idLibrarian, idCopy);
        completedOrdersRepository.save(ordersEntity);
    }

    public List<CompletedOrders> findAllByUserId(Long userId) {
        List<CompletedOrders> allByIdReader = completedOrdersRepository.findAllByIdReader(userId);
        if (!allByIdReader.isEmpty()) {
            return allByIdReader;
        } else throw new OrderNotExistException("User is currently not reading books.");
    }

    public List<CompletedOrders> findAllCompletedOrders() {
        final List<CompletedOrders> completedOrdersList = completedOrdersRepository.findAll();
        if (!completedOrdersList.isEmpty()) {
            return completedOrdersList;
        } else throw new OrderNotExistException("No books have been taken to read by users..");
    }
}
