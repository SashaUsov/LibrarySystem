package com.servletProject.librarySystem.facade;

import com.servletProject.librarySystem.domen.dto.archiveBookUsage.ArchiveBookModel;
import com.servletProject.librarySystem.exception.DataIsNotCorrectException;
import com.servletProject.librarySystem.service.LibrarianControllerService;
import com.servletProject.librarySystem.utils.OrdersUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibrarianArchiveActionsController {

    private final LibrarianControllerService librarianControllerService;

    public LibrarianArchiveActionsController(LibrarianControllerService librarianControllerService) {
        this.librarianControllerService = librarianControllerService;
    }

    public List<ArchiveBookModel> getAllUsageArchive() {
        return librarianControllerService.getListOfAllArchiveUsage();
    }

    public List<ArchiveBookModel> getAllByUser(String email) {
        if (OrdersUtil.ifEmailPresent(email)) {
            return librarianControllerService.getListOfActiveUsageByUser(email);
        } else throw new DataIsNotCorrectException("Enter the correct email and try again.");
    }
}
