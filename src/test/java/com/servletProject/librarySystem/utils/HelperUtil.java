package com.servletProject.librarySystem.utils;

import com.servletProject.librarySystem.domen.*;
import com.servletProject.librarySystem.domen.dto.bookCatalog.CreateBookCatalogModel;
import com.servletProject.librarySystem.domen.dto.userEntity.CreateUserEntityModel;

import java.util.ArrayList;
import java.util.List;

public class HelperUtil {

    public static UserEntity getUser() {
        var expected = new UserEntity();
        expected.setId(2);
        expected.setFirstName("Sasha");
        expected.setLastName("Sasha");
        expected.setNickName("Sasha");
        expected.setMail("sasha@gmail.com");
        expected.setPassword("12345");
        expected.setAddress("Kyiv");
        expected.setActive(true);
        expected.setPermission(true);
        expected.getRoles().add(Role.USER);

        return expected;
    }

    public static CreateUserEntityModel getModel() {
        var model = new CreateUserEntityModel();
        model.setFirstName("Sasha");
        model.setLastName("Sasha");
        model.setNickName("Sasha");
        model.setMail("sasha@gmail.com");
        model.setPassword("12345");
        model.setAddress("Kyiv");

        return model;
    }

    public static ArchiveBookUsage getArchiveBookUsageEntity() {
        var entity = new ArchiveBookUsage();

        entity.setId(5);
        entity.setIdReader(1);
        entity.setIdCopiesBook(2);
        entity.setBookCondition("good");

        return entity;
    }

    public static List<ArchiveBookUsage> getArchiveBookUsageList() {
        var entityOne = new ArchiveBookUsage();

        entityOne.setId(5);
        entityOne.setIdReader(1);
        entityOne.setIdCopiesBook(2);
        entityOne.setBookCondition("good");

        var entityTwo = new ArchiveBookUsage();

        entityTwo.setId(9);
        entityTwo.setIdReader(1);
        entityTwo.setIdCopiesBook(6);
        entityTwo.setBookCondition("good");

        List<ArchiveBookUsage> archive = new ArrayList<>();
        archive.add(entityOne);
        archive.add(entityTwo);

        return archive;
    }

    public static CreateBookCatalogModel getCreateBookCatalogModel() {
        var model = new CreateBookCatalogModel();

        model.setBookTitle("Title");
        model.setBookAuthor("Author");
        model.setGenre("Genre");
        model.setYearOfPublication(2019);

        return model;
    }

    public static BookCatalog getBookCatalogEntity() {
        var model = new BookCatalog();

        model.setBookTitle("Title");
        model.setBookAuthor("Author");
        model.setGenre("Genre");
        model.setYearOfPublication(2019);
        model.setId(1);

        return model;
    }

    public static CopiesOfBooks getCopiesOfBooksEntity() {
        var model = new CopiesOfBooks();

        model.setAvailability(true);
        model.setBookCondition("good");
        model.setIdBook(1);
        model.setId(2);

        return model;
    }

    public static CompletedOrders getCompletedOrders() {
        var model = new CompletedOrders();

        model.setIdLibrarian(3);
        model.setIdBook(1);
        model.setId(2);

        return model;
    }

    public static OnlineOrderBook getOnlineOrderBook() {
        var model = new OnlineOrderBook();

        model.setId(1);
        model.setIdUser(2);
        model.setIdBookCopy(3);

        return model;
    }
}
