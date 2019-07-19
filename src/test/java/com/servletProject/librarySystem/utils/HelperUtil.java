package com.servletProject.librarySystem.utils;

import com.servletProject.librarySystem.domen.ArchiveBookUsage;
import com.servletProject.librarySystem.domen.Role;
import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.domen.dto.userEntity.CreateUserEntityModel;

import java.util.ArrayList;
import java.util.List;

public class HelperUtil {

    public static UserEntity getUser() {
        UserEntity expected = new UserEntity();
        expected.setId(1);
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
        CreateUserEntityModel model = new CreateUserEntityModel();
        model.setFirstName("Sasha");
        model.setLastName("Sasha");
        model.setNickName("Sasha");
        model.setMail("sasha@gmail.com");
        model.setPassword("12345");
        model.setAddress("Kyiv");

        return model;
    }

    public static ArchiveBookUsage getArchiveBookUsageEntity() {
        ArchiveBookUsage entity = new ArchiveBookUsage();

        entity.setId(5);
        entity.setIdReader(1);
        entity.setIdCopiesBook(2);
        entity.setBookCondition("good");

        return entity;
    }

    public static List<ArchiveBookUsage> getArchiveBookUsageList() {
        ArchiveBookUsage entityOne = new ArchiveBookUsage();

        entityOne.setId(5);
        entityOne.setIdReader(1);
        entityOne.setIdCopiesBook(2);
        entityOne.setBookCondition("good");

        ArchiveBookUsage entityTwo = new ArchiveBookUsage();

        entityTwo.setId(9);
        entityTwo.setIdReader(1);
        entityTwo.setIdCopiesBook(6);
        entityTwo.setBookCondition("good");

        List<ArchiveBookUsage> archive = new ArrayList<>();
        archive.add(entityOne);
        archive.add(entityTwo);

        return archive;
    }
}
