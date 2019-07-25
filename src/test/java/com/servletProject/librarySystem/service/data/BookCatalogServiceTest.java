package com.servletProject.librarySystem.service.data;

import com.servletProject.librarySystem.domen.BookCatalog;
import com.servletProject.librarySystem.domen.CopiesOfBooks;
import com.servletProject.librarySystem.exception.ThereAreNoBooksFoundException;
import com.servletProject.librarySystem.repository.BookRepository;
import com.servletProject.librarySystem.repository.CopiesOfBooksRepository;
import com.servletProject.librarySystem.utils.HelperUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookCatalogServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CopiesOfBooksRepository copiesOfBooksRepository;

    @InjectMocks
    private BookCatalogService bookCatalogService;

    @Test
    public void shouldSaveOriginalBookAndDoNothingWhenSaveBookMethodCall() {
        var model = HelperUtil.getCreateBookCatalogModel();
        var book = HelperUtil.getBookCatalogEntity();
        var copy = HelperUtil.getCopiesOfBooksEntity();

        when(bookRepository.findOneByBookTitle(anyString()))
                .thenReturn(Optional.empty());
        when(bookRepository.save(any(BookCatalog.class)))
                .thenReturn(book);
        when(copiesOfBooksRepository.save(any(CopiesOfBooks.class)))
                .thenReturn(copy);

        bookCatalogService.saveBook(model);

        verify(bookRepository).findOneByBookTitle(anyString());
        verify(bookRepository).save(any(BookCatalog.class));
        verify(copiesOfBooksRepository).save(any(CopiesOfBooks.class));
    }

    @Test
    public void shouldSaveOnlyBookCopyAndDoNothingWhenSaveBookMethodCall() {
        var model = HelperUtil.getCreateBookCatalogModel();
        var book = HelperUtil.getBookCatalogEntity();
        var copy = HelperUtil.getCopiesOfBooksEntity();

        when(bookRepository.findOneByBookTitle(anyString()))
                .thenReturn(Optional.of(book));
        when(copiesOfBooksRepository.save(any(CopiesOfBooks.class)))
                .thenReturn(copy);
        doNothing().when(bookRepository).incrementBookTotalAmount(anyLong());

        bookCatalogService.saveBook(model);

        verify(bookRepository).findOneByBookTitle(anyString());
        verify(copiesOfBooksRepository).save(any(CopiesOfBooks.class));
        verify(bookRepository).incrementBookTotalAmount(anyLong());
    }

    @Test(expected = ThereAreNoBooksFoundException.class)
    public void shouldThrowThereAreNoBooksFoundExceptionWhenTryGetAllBookMethod() {
        when(bookRepository.findAll())
                .thenReturn(Collections.emptyList());
        bookCatalogService.getAllBook();
    }

    @Test
    public void shouldReturnBookCatalogListWhenTryGetAllBookMethod() {
        var book = HelperUtil.getBookCatalogEntity();
        var given = Collections.singletonList(book);

        when(bookRepository.findAll())
                .thenReturn(given);
        var actual = bookCatalogService.getAllBook();

        assertEquals(given, actual);
    }

    @Test(expected = ThereAreNoBooksFoundException.class)
    public void shouldThrowThereAreNoBooksFoundExceptionWhenTryGetAllBookByTitleMethod() {
        when(bookRepository.findAllByBookTitleContaining(anyString()))
                .thenReturn(Collections.emptyList());
        bookCatalogService.getAllBookByTitle(anyString());
    }

    @Test
    public void shouldReturnBookCatalogListWhenTryGetAllBookByTitleMethod() {
        var book = HelperUtil.getBookCatalogEntity();

        when(bookRepository.findAllByBookTitleContaining(anyString()))
                .thenReturn(Collections.singletonList(book));
        List<BookCatalog> actual = bookCatalogService.getAllBookByTitle(anyString());

        assertEquals(Collections.singletonList(book), actual);
    }

    @Test(expected = ThereAreNoBooksFoundException.class)
    public void shouldThrowThereAreNoBooksFoundExceptionWhenTryGetAllBookByAuthorMethod() {
        when(bookRepository.findAllByBookAuthorContaining(anyString()))
                .thenReturn(Collections.emptyList());
        bookCatalogService.getAllBookByAuthor(anyString());
    }

    @Test
    public void shouldReturnBookCatalogListWhenTryGetAllBookByAuthorMethod() {
        var book = HelperUtil.getBookCatalogEntity();

        when(bookRepository.findAllByBookAuthorContaining(anyString()))
                .thenReturn(Collections.singletonList(book));

        var actual = bookCatalogService.getAllBookByAuthor(anyString());

        assertEquals(Collections.singletonList(book), actual);
    }

    @Test(expected = ThereAreNoBooksFoundException.class)
    public void shouldThrowThereAreNoBooksFoundExceptionWhenTryGetAllBookByGenreMethod() {
        when(bookRepository.findAllByGenreContaining(anyString()))
                .thenReturn(Collections.emptyList());
        bookCatalogService.getAllBookByGenre(anyString());
    }

    @Test
    public void shouldReturnBookCatalogListWhenTryGetAllBookByGenreMethod() {
        var book = HelperUtil.getBookCatalogEntity();

        when(bookRepository.findAllByGenreContaining(anyString()))
                .thenReturn(Collections.singletonList(book));
        var actual = bookCatalogService.getAllBookByGenre(anyString());

        assertEquals(Collections.singletonList(book), actual);
    }

    @Test(expected = ThereAreNoBooksFoundException.class)
    public void shouldThrowThereAreNoBooksFoundExceptionWhenTryFindAllByIdInMethod() {
        when(bookRepository.findAllByIdIn(anyCollection()))
                .thenReturn(Collections.emptyList());
        bookCatalogService.findAllByIdIn(anyList());
    }

    @Test
    public void shouldReturnBookCatalogListWhenTryFindAllByIdInMethod() {
        var book = HelperUtil.getBookCatalogEntity();

        when(bookRepository.findAllByIdIn(anyCollection()))
                .thenReturn(Collections.singletonList(book));
        var actual = bookCatalogService.findAllByIdIn(anyList());

        assertEquals(Collections.singletonList(book), actual);
    }
}
