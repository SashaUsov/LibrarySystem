package com.servletProject.librarySystem.controller;

import com.servletProject.librarySystem.domen.dto.archiveBookUsage.ArchiveBookModel;
import com.servletProject.librarySystem.exception.DataIsNotCorrectException;
import com.servletProject.librarySystem.service.LibrarianControllerService;
import com.servletProject.librarySystem.utils.OrdersUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("librarian-archive")
public class LibrarianArchiveActionsController {

    private final LibrarianControllerService librarianControllerService;

    public LibrarianArchiveActionsController(LibrarianControllerService librarianControllerService) {
        this.librarianControllerService = librarianControllerService;
    }

    @GetMapping("all")
    public List<ArchiveBookModel> getAllUsageArchive() {
        return librarianControllerService.getListOfAllArchiveUsage();
    }

    @GetMapping("all/{email}")
    public List<ArchiveBookModel> getAllByUser(@PathVariable("email") String email) {
        if (OrdersUtil.ifEmailPresent(email)) {
            return librarianControllerService.getListOfActiveUsageByUser(email);
        } else throw new DataIsNotCorrectException("Enter the correct email and try again.");
    }
}
