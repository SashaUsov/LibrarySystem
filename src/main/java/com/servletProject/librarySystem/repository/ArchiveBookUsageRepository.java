package com.servletProject.librarySystem.repository;

import com.servletProject.librarySystem.domen.ArchiveBookUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface ArchiveBookUsageRepository extends JpaRepository<ArchiveBookUsage, Long> {

    List<ArchiveBookUsage> findAllByIdReader(Long idReader);

    @Modifying
    void deleteAllByIdCopiesBook(long id);
}
