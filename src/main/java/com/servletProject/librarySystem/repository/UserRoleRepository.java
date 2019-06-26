package com.servletProject.librarySystem.repository;

import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.domen.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface UserRoleRepository extends JpaRepository<Role, Long> {

    @Modifying
    void deleteUserRoleByUserIdAndRole(UserEntity user, String role);

    String findOneByUserId(UserEntity user);
}
