package com.servletProject.librarySystem.service.data;

import com.servletProject.librarySystem.domen.OnlineOrderBook;
import com.servletProject.librarySystem.exception.OrderNotExistException;
import com.servletProject.librarySystem.exception.PermissionToActionIsAbsentException;
import com.servletProject.librarySystem.exception.ThereAreNoBooksFoundException;
import com.servletProject.librarySystem.repository.OnlineOrderBookRepository;
import com.servletProject.librarySystem.utils.HelperUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OnlineOrderBookServiceTest {

    @InjectMocks
    private OnlineOrderBookService onlineOrderBookService;

    @Mock
    private OnlineOrderBookRepository onlineOrderBookRepository;

    @Test(expected = ThereAreNoBooksFoundException.class)
    public void testFindAllByUserIdShouldTrowThereAreNoBooksFoundException() {
        when(onlineOrderBookRepository.findAllByIdUser(anyLong()))
                .thenReturn(Collections.emptyList());
        onlineOrderBookService.findAllByUserId(anyLong());
    }

    @Test
    public void testFindAllByUserIdShouldReturnOnlineOrderBookList() {
        var model = HelperUtil.getOnlineOrderBook();
        when(onlineOrderBookRepository.findAllByIdUser(anyLong()))
                .thenReturn(Collections.singletonList(model));
        var actual = onlineOrderBookService.findAllByUserId(anyLong());

        verify(onlineOrderBookRepository, times(1)).findAllByIdUser(anyLong());
        assertEquals(Collections.singletonList(model), actual);
    }

    @Test(expected = ThereAreNoBooksFoundException.class)
    public void testFindAllOrdersShouldTrowThereAreNoBooksFoundException() {
        when(onlineOrderBookRepository.findAll())
                .thenReturn(Collections.emptyList());
        onlineOrderBookService.findAllOrders();
    }

    @Test
    public void testFindAllOrdersShouldReturnOnlineOrderBookList() {
        var model = HelperUtil.getOnlineOrderBook();
        when(onlineOrderBookRepository.findAll())
                .thenReturn(Collections.singletonList(model));
        var actual = onlineOrderBookService.findAllOrders();

        verify(onlineOrderBookRepository, times(1)).findAll();
        assertEquals(Collections.singletonList(model), actual);
    }

    @Test(expected = OrderNotExistException.class)
    public void testDeleteOrderByCopyIdAndUserIdShouldThrowOrderNotExistException() {
        when(onlineOrderBookRepository.findOneByIdUserAndIdBookCopy(anyLong(), anyLong()))
                .thenReturn(Optional.empty());
        onlineOrderBookService.deleteOrderByCopyIdAndUserId(anyLong(), anyLong());
    }

    @Test
    public void testDeleteOrderByCopyIdAndUserIdShouldDoNothing() {
        when(onlineOrderBookRepository.findOneByIdUserAndIdBookCopy(anyLong(), anyLong()))
                .thenReturn(Optional.of(new OnlineOrderBook()));
        doNothing().when(onlineOrderBookRepository).removeByIdBookCopyAndIdUser(anyLong(), anyLong());
        onlineOrderBookService.deleteOrderByCopyIdAndUserId(anyLong(), anyLong());

        verify(onlineOrderBookRepository, times(1))
                .findOneByIdUserAndIdBookCopy(anyLong(), anyLong());
        verify(onlineOrderBookRepository, times(1))
                .removeByIdBookCopyAndIdUser(anyLong(), anyLong());
    }

    @Test(expected = OrderNotExistException.class)
    public void testCancelOrderByReaderShouldThrowOrderNotExistException() {
        when(onlineOrderBookRepository.findOneByIdBookCopy(anyLong()))
                .thenReturn(Optional.empty());

        onlineOrderBookService.cancelOrderByReader(anyLong(), HelperUtil.getUser()); //???
    }

    @Test(expected = PermissionToActionIsAbsentException.class)
    public void testCancelOrderByReaderShouldThrowPermissionToActionIsAbsentException() {
        when(onlineOrderBookRepository.findOneByIdBookCopy(anyLong()))
                .thenReturn(Optional.of(HelperUtil.getOnlineOrderBook()));

        onlineOrderBookService.cancelOrderByReader(anyLong(), HelperUtil.getUser()); //????
    }

    @Test
    public void testCancelOrderByReaderShouldDoNothing() {
        var order = HelperUtil.getOnlineOrderBook();
        var user = HelperUtil.getUser();

        when(onlineOrderBookRepository.findOneByIdBookCopy(anyLong()))
                .thenReturn(Optional.of(order));
        doNothing().when(onlineOrderBookRepository).delete(any(OnlineOrderBook.class));
        onlineOrderBookService.cancelOrderByReader(order.getIdBookCopy(), user);

        verify(onlineOrderBookRepository, times(1))
                .findOneByIdBookCopy(anyLong());
        verify(onlineOrderBookRepository, times(1))
                .delete(any(OnlineOrderBook.class));
    }

    @Test(expected = OrderNotExistException.class)
    public void testCancelOrderLibrarianShouldThrowOrderNotExistException() {
        when(onlineOrderBookRepository.findOneByIdBookCopy(anyLong()))
                .thenReturn(Optional.empty());

        onlineOrderBookService.cancelOrderLibrarian(1L, 2L); //???
    }

    @Test
    public void testCancelOrderByLibrarianShouldDoNothing() {
        var order = HelperUtil.getOnlineOrderBook();
        var user = HelperUtil.getUser();

        when(onlineOrderBookRepository.findOneByIdBookCopy(anyLong()))
                .thenReturn(Optional.of(order));

        doNothing().when(onlineOrderBookRepository).delete(any(OnlineOrderBook.class));

        onlineOrderBookService.cancelOrderLibrarian(1L, user.getId());

        verify(onlineOrderBookRepository, times(1))
                .findOneByIdBookCopy(anyLong());
        verify(onlineOrderBookRepository, times(1))
                .delete(any(OnlineOrderBook.class));
    }

    @Test
    public void testReserveBookCopyShouldDoNothing() {
        when(onlineOrderBookRepository.save(any(OnlineOrderBook.class))).thenReturn(any());

        onlineOrderBookService.reserveBookCopy(1L, 1L);

        verify(onlineOrderBookRepository, times(1))
                .save(any(OnlineOrderBook.class));
    }
}
