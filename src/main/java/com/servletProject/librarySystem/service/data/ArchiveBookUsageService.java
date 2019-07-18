package com.servletProject.librarySystem.service.data;

import com.servletProject.librarySystem.domen.ArchiveBookUsage;
import com.servletProject.librarySystem.exception.ThereAreNoBooksFoundException;
import com.servletProject.librarySystem.repository.ArchiveBookUsageRepository;
import com.servletProject.librarySystem.utils.CreateEntityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ArchiveBookUsageService {

    private final ArchiveBookUsageRepository usageRepository;

    public ArchiveBookUsageService(ArchiveBookUsageRepository usageRepository) {
        this.usageRepository = usageRepository;
    }

    public void putBookInUsageArchive(Long readerId, Long copyId, String condition) {
        ArchiveBookUsage entity = CreateEntityUtil.createArchiveBookUsageEntity(readerId, copyId, condition);
        usageRepository.save(entity);
        log.info("Book with id=" + copyId + " put in usage archive");
    }

    public List<ArchiveBookUsage> findAllByUserId(Long idUser) {
        List<ArchiveBookUsage> bookUsageList = usageRepository.findAllByIdReader(idUser);
        if (bookUsageList != null && !bookUsageList.isEmpty()) {
            return bookUsageList;
        } else throw new ThereAreNoBooksFoundException("No books found in user archive");
    }

    public List<ArchiveBookUsage> findAllUsageArchive() {
        List<ArchiveBookUsage> bookUsageList = usageRepository.findAll();
        if (!bookUsageList.isEmpty()) {
            return bookUsageList;
        } else throw new ThereAreNoBooksFoundException("No books found in user archive");
    }
}
