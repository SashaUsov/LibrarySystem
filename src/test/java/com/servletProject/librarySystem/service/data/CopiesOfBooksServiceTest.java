package com.servletProject.librarySystem.service.data;

import com.servletProject.librarySystem.domen.CopiesOfBooks;
import com.servletProject.librarySystem.exception.ThereAreNoBooksFoundException;
import com.servletProject.librarySystem.repository.ArchiveBookUsageRepository;
import com.servletProject.librarySystem.repository.BookRepository;
import com.servletProject.librarySystem.repository.CopiesOfBooksRepository;
import com.servletProject.librarySystem.utils.HelperUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CopiesOfBooksServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CopiesOfBooksRepository copiesOfBooksRepository;

    @Mock
    private ArchiveBookUsageRepository archiveBookUsageRepository;

    @InjectMocks
    private CopiesOfBooksService copiesOfBooksService;

    @Test(expected = ThereAreNoBooksFoundException.class)
    public void testDeleteUnusableBookCopyShouldThrowThereAreNoBooksFoundException() {
        when(copiesOfBooksRepository.findOneByIdAndAndAvailabilityTrue(anyLong()))
                .thenReturn(Optional.empty());
        copiesOfBooksService.deleteUnusableBookCopy(1L);
        verify(copiesOfBooksRepository, times(1)).findOneByIdAndAndAvailabilityTrue(anyLong());
    }

    @Test
    public void testDeleteUnusableBookCopyShouldDoNothing() {
        when(copiesOfBooksRepository.findOneByIdAndAndAvailabilityTrue(anyLong()))
                .thenReturn(Optional.of(HelperUtil.getCopiesOfBooksEntity()));
        doNothing().when(archiveBookUsageRepository).deleteAllByIdCopiesBook(anyLong());
        doNothing().when(bookRepository).decrementBookTotalAmount(anyLong());
        doNothing().when(copiesOfBooksRepository).delete(any(CopiesOfBooks.class));

        copiesOfBooksService.deleteUnusableBookCopy(1L);

        verify(copiesOfBooksRepository, times(1)).findOneByIdAndAndAvailabilityTrue(anyLong());
        verify(archiveBookUsageRepository, times(1)).deleteAllByIdCopiesBook(anyLong());
        verify(bookRepository, times(1)).decrementBookTotalAmount(anyLong());
        verify(copiesOfBooksRepository, times(1)).delete(any(CopiesOfBooks.class));
    }

    @Test
    public void testUpdateAvailabilityOfCopyShouldDoNothing() {
        doNothing().when(copiesOfBooksRepository).updateAvailabilityById(anyLong(), anyBoolean());

        copiesOfBooksService.updateAvailabilityOfCopy(true, 1L);

        verify(copiesOfBooksRepository, times(1))
                .updateAvailabilityById(anyLong(), anyBoolean());
    }

    @Test(expected = ThereAreNoBooksFoundException.class)
    public void testFindAllByIdShouldThrowThereAreNoBooksFoundException() {
        when(copiesOfBooksRepository.findAllByIdIn(anyList()))
                .thenReturn(Collections.emptyList());

        copiesOfBooksService.findAllById(Collections.singletonList(1L));

        verify(copiesOfBooksRepository, times(1))
                .findAllByIdIn(anyList());
    }

    @Test
    public void testFindAllByIdShouldReturnCopiesOfBookList() {
        var expected = Collections.singletonList(HelperUtil.getCopiesOfBooksEntity());
        when(copiesOfBooksRepository.findAllByIdIn(anyList()))
                .thenReturn(expected);

        var actual = copiesOfBooksService.findAllById(Collections.singletonList(1L));

        verify(copiesOfBooksRepository, times(1))
                .findAllByIdIn(anyList());
        assertEquals(expected, actual);
    }

    @Test(expected = ThereAreNoBooksFoundException.class)
    public void testFindAllCopyShouldThrowThereAreNoBooksFoundException() {
        when(copiesOfBooksRepository.findAllByIdBook(anyLong()))
                .thenReturn(Collections.emptyList());

        copiesOfBooksService.findAllCopy(1L);

        verify(copiesOfBooksRepository, times(1))
                .findAllByIdBook(anyLong());
    }

    @Test
    public void testFindAllCopyShouldReturnCopiesOfBookList() {
        var expected = Collections.singletonList(HelperUtil.getCopiesOfBooksEntity());
        when(copiesOfBooksRepository.findAllByIdBook(anyLong()))
                .thenReturn(expected);

        var actual = copiesOfBooksService.findAllCopy(1L);

        verify(copiesOfBooksRepository, times(1))
                .findAllByIdBook(anyLong());
        assertEquals(expected, actual);
    }

    @Test(expected = ThereAreNoBooksFoundException.class)
    public void testUpdateCopyOfBookInfoShouldThrowThereAreNoBooksFoundException() {
        when(copiesOfBooksRepository.findOneByIdAndAndAvailabilityFalse(anyLong()))
                .thenReturn(Optional.empty());

        copiesOfBooksService.updateCopyOfBookInfo(1L, "good");

        verify(copiesOfBooksRepository, times(1))
                .findOneByIdAndAndAvailabilityFalse(anyLong());
    }

    @Test
    public void testUpdateCopyOfBookInfoShouldDoNothing() {
        when(copiesOfBooksRepository.findOneByIdAndAndAvailabilityFalse(anyLong()))
                .thenReturn(Optional.of(HelperUtil.getCopiesOfBooksEntity()));
        doNothing().when(copiesOfBooksRepository)
                .updateAvailabilityAndConditionOfCopy(anyLong(), anyString(), anyBoolean());

        copiesOfBooksService.updateCopyOfBookInfo(1L, "good");

        verify(copiesOfBooksRepository, times(1))
                .findOneByIdAndAndAvailabilityFalse(anyLong());
        verify(copiesOfBooksRepository, times(1))
                .updateAvailabilityAndConditionOfCopy(anyLong(), anyString(), anyBoolean());
    }

    @Test(expected = ThereAreNoBooksFoundException.class)
    public void testGetAllCopyByConditionThrowThereAreNoBooksFoundException() {
        when(copiesOfBooksRepository.findAllByBookConditionAndAvailabilityTrue(anyString()))
                .thenReturn(Collections.emptyList());

        copiesOfBooksService.getAllCopyByCondition("good");

        verify(copiesOfBooksRepository, times(1))
                .findAllByBookConditionAndAvailabilityTrue(anyString());
    }

    @Test
    public void testGetAllCopyByConditionShouldReturnCopiesOfBookList() {
        var expected = Collections.singletonList(HelperUtil.getCopiesOfBooksEntity());
        when(copiesOfBooksRepository.findAllByBookConditionAndAvailabilityTrue(anyString()))
                .thenReturn(expected);

        var actual = copiesOfBooksService.getAllCopyByCondition("good");

        verify(copiesOfBooksRepository, times(1))
                .findAllByBookConditionAndAvailabilityTrue(anyString());
        assertEquals(expected, actual);
    }
}
