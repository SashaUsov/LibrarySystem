package com.servletProject.librarySystem.service.data;

import com.servletProject.librarySystem.domen.ArchiveBookUsage;
import com.servletProject.librarySystem.exception.ThereAreNoBooksFoundException;
import com.servletProject.librarySystem.repository.ArchiveBookUsageRepository;
import com.servletProject.librarySystem.utils.HelperUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArchiveBookUsageServiceTest {

    @Mock
    private ArchiveBookUsageRepository archiveBookUsageRepository;

    @InjectMocks
    private ArchiveBookUsageService archiveBookUsageService;

    @Test
    public void shouldDoNothingWhenTryPutBookInUsageArchive() {
        var archiveBookUsageEntity = HelperUtil.getArchiveBookUsageEntity();

        when(archiveBookUsageRepository.save(any(ArchiveBookUsage.class)))
                .thenReturn(archiveBookUsageEntity);
        archiveBookUsageService.putBookInUsageArchive(archiveBookUsageEntity.getIdReader(),
                archiveBookUsageEntity.getIdCopiesBook(),
                archiveBookUsageEntity.getBookCondition());

        verify(archiveBookUsageRepository).save(any(ArchiveBookUsage.class));
    }

    @Test(expected = ThereAreNoBooksFoundException.class)
    public void shouldTrowThereAreNoBooksFoundExceptionWhenFindAllByUserIdAndUserIdIsNotExist() {
        when(archiveBookUsageRepository.findAllByIdReader(anyLong())).thenReturn(null);

        archiveBookUsageService.findAllByUserId(anyLong());
    }

    @Test
    public void shouldReturnArchiveBookUsageListWhenFindAllByUserId() {
        var expected = HelperUtil.getArchiveBookUsageList();
        when(archiveBookUsageRepository.findAllByIdReader(anyLong()))
                .thenReturn(expected);
        var actual = archiveBookUsageService.findAllByUserId(anyLong());

        assertEquals(expected, actual);
    }

    @Test(expected = ThereAreNoBooksFoundException.class)
    public void shouldTrowThereAreNoBooksFoundExceptionWhenFindAllUsageArchive() {
        when(archiveBookUsageRepository.findAll()).thenReturn(Collections.emptyList());

        archiveBookUsageService.findAllUsageArchive();
    }

    @Test
    public void shouldReturnArchiveBookUsageListWhenFindAllUsageArchive() {
        var expected = HelperUtil.getArchiveBookUsageList();
        when(archiveBookUsageRepository.findAll())
                .thenReturn(expected);
        var actual = archiveBookUsageService.findAllUsageArchive();

        assertEquals(expected, actual);
    }
}
