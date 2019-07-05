package com.servletProject.librarySystem.repository;

import com.servletProject.librarySystem.domen.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findOneByMail(String email);

    Optional<UserEntity> findOneById(Long id);

    Optional<UserEntity> findOneByNickName(String nickName);

    @Query(value = "SELECT CONCAT(u.first_name, ' ' ,u.last_name) FROM user_entity u WHERE u.id=: idUser",
            nativeQuery = true)
    String findFullUserName(@Param("idUser") Long userId);

}
