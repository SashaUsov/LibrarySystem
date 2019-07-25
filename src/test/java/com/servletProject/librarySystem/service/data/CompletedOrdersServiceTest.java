package com.servletProject.librarySystem.service.data;

import com.servletProject.librarySystem.domen.CompletedOrders;
import com.servletProject.librarySystem.exception.OrderNotExistException;
import com.servletProject.librarySystem.repository.CompletedOrdersRepository;
import com.servletProject.librarySystem.utils.HelperUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompletedOrdersServiceTest {

    @Mock
    private CompletedOrdersRepository completedOrdersRepository;

    @InjectMocks
    private CompletedOrdersService completedOrdersService;

    @Test
    public void shouldDoNothingWhenSaveOrderMethodTry() {
        var order = HelperUtil.getCompletedOrders();
        when(completedOrdersRepository.save(any(CompletedOrders.class)))
                .thenReturn(new CompletedOrders());
        completedOrdersService.saveOrder(order.getIdReader(), order.getIdLibrarian(),order.getIdBook());

        verify(completedOrdersRepository).save(any(CompletedOrders.class));
    }

    @Test(expected = OrderNotExistException.class)
    public void shouldThrowOrderNotExistExceptionWhenTryDeleteFromCompletedOrdersByCopyIdMethod() {
        when(completedOrdersRepository.findOneByIdBook(anyLong()))
                .thenReturn(Optional.empty());
        completedOrdersService.deleteFromCompletedOrdersByCopyId(anyLong());
    }

    @Test
    public void shouldDoNothingWhenTryDeleteFromCompletedOrdersByCopyIdMethod() {
        var model = HelperUtil.getCompletedOrders();

        when(completedOrdersRepository.findOneByIdBook(anyLong()))
                .thenReturn(Optional.of(model));

        doNothing().when(completedOrdersRepository).delete(any(CompletedOrders.class));
        completedOrdersService.deleteFromCompletedOrdersByCopyId(anyLong());

        verify(completedOrdersRepository).findOneByIdBook(anyLong());
        verify(completedOrdersRepository).delete(any(CompletedOrders.class));
    }

    @Test(expected = OrderNotExistException.class)
    public void shouldThrowOrderNotExistExceptionWhenTryFindAllCompletedOrdersMethod() {
        when(completedOrdersRepository.findAll())
                .thenReturn(Collections.emptyList());
        completedOrdersService.findAllCompletedOrders();
    }

    @Test
    public void shouldReturnCompletedOrdersListWhenTryFindAllCompletedOrdersMethod() {
        var model = HelperUtil.getCompletedOrders();

        when(completedOrdersRepository.findAll())
                .thenReturn(Collections.singletonList(model));
        var actual = completedOrdersService.findAllCompletedOrders();

        verify(completedOrdersRepository).findAll();
        assertEquals(Collections.singletonList(model), actual);
    }

    @Test(expected = OrderNotExistException.class)
    public void shouldThrowOrderNotExistExceptionWhenTryFindAllByUserIdMethod() {
        when(completedOrdersRepository.findAllByIdReader(anyLong()))
                .thenReturn(Collections.emptyList());
        completedOrdersService.findAllByUserId(anyLong());
    }

    @Test
    public void shouldReturnCompletedOrdersListWhenTryFindAllByUserIdMethod() {
        var model = HelperUtil.getCompletedOrders();

        when(completedOrdersRepository.findAllByIdReader(anyLong()))
                .thenReturn(Collections.singletonList(model));
        var actual = completedOrdersService.findAllByUserId(anyLong());

        verify(completedOrdersRepository).findAllByIdReader(anyLong());
        assertEquals(Collections.singletonList(model), actual);
    }
}
