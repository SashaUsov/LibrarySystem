package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.domen.dto.bookCatalog.CreateBookCatalogModel;
import com.servletProject.librarySystem.exception.DataIsNotCorrectException;
import com.servletProject.librarySystem.service.data.BookCatalogService;
import com.servletProject.librarySystem.service.data.CopiesOfBooksService;
import com.servletProject.librarySystem.utils.HelperUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerServiceTest {

    @Mock
    private BookCatalogService bookCatalogService;
    @Mock
    private CopiesOfBooksService copiesOfBooksService;
    @InjectMocks
    private BookControllerService bookControllerService;

    @Test(expected = DataIsNotCorrectException.class)
    public void testAddNewBookThrowDataIsNotCorrectException() {
        bookControllerService.addNewBook(null);
    }

    @Test
    public void testAddNewBookShouldDoNothing() {
        doNothing().when(bookCatalogService).saveBook(any(CreateBookCatalogModel.class));

        bookControllerService.addNewBook(HelperUtil.getCreateBookCatalogModel());

        verify(bookCatalogService, times(1))
                .saveBook(any(CreateBookCatalogModel.class));
    }

    @Test
    public void testGetAllBookShouldReturnBookCatalogList() {
        var expected = Collections.singletonList(HelperUtil.getBookCatalogEntity());
        when(bookCatalogService.getAllBook())
                .thenReturn(expected);

        var actual = bookControllerService.getAllBook();

        verify(bookCatalogService, times(1)).getAllBook();
        assertEquals(expected, actual);
    }

    @Test
    public void testFindAllCopyShouldReturnCopiesOfBookList() {
        var expected = Collections.singletonList(HelperUtil.getCopiesOfBooksEntity());
        when(copiesOfBooksService.findAllCopy(anyLong()))
                .thenReturn(expected);

        var actual = bookControllerService.findAllCopy(1L);

        verify(copiesOfBooksService, times(1)).findAllCopy(anyLong());
        assertEquals(expected, actual);
    }
}
