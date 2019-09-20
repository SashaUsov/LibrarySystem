package com.servletProject.librarySystem.service.data;

import com.servletProject.librarySystem.domen.CompletedOrders;
import com.servletProject.librarySystem.exception.OrderNotExistException;
import com.servletProject.librarySystem.repository.CompletedOrdersRepository;
import com.servletProject.librarySystem.utils.CreateEntityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CompletedOrdersService {

    private final CompletedOrdersRepository completedOrdersRepository;

    public CompletedOrdersService(CompletedOrdersRepository completedOrdersRepository) {
        this.completedOrdersRepository = completedOrdersRepository;
    }

    public void saveOrder(Long idUser, Long idLibrarian, Long idCopy) {
        var ordersEntity = CreateEntityUtil.createCompletedOrdersEntity(idUser, idLibrarian, idCopy);
        completedOrdersRepository.save(ordersEntity);
        log.info("New order created : idCopy=" + idCopy
                + " idUser=" + idUser);
    }

    public List<CompletedOrders> findAllByUserId(Long userId) {
        var allByIdReader = completedOrdersRepository.findAllByIdReader(userId);
        if (!allByIdReader.isEmpty()) {
            return allByIdReader;
        } else throw new OrderNotExistException("User is currently not reading books.");
    }

    public List<CompletedOrders> findAllCompletedOrders() {
        var completedOrdersList = completedOrdersRepository.findAll();
        if (!completedOrdersList.isEmpty()) {
            return completedOrdersList;
        } else throw new OrderNotExistException("No books have been taken to read by users..");
    }

    public void deleteFromCompletedOrdersByCopyId(Long idCopy) {
        var completedOrders = completedOrdersRepository.findOneByIdBook(idCopy);
        var order = completedOrders.orElseThrow(() -> new OrderNotExistException("Order not found."));
        completedOrdersRepository.delete(order);
        log.info("Book returned to catalog. idUser="
                + order.getIdReader() + " idCopy=" + order.getIdBook());
    }
}
