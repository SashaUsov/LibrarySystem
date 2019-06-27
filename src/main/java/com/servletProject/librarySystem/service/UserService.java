package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.converter.UserEntityConverter;
import com.servletProject.librarySystem.dao.transactionManager.TransactionManager;
import com.servletProject.librarySystem.domen.Role;
import com.servletProject.librarySystem.domen.Roles;
import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.domen.dto.userEntity.CreateUserEntityModel;
import com.servletProject.librarySystem.domen.dto.userEntity.UserEntityModel;
import com.servletProject.librarySystem.exception.AuthorisationException;
import com.servletProject.librarySystem.exception.ClientAlreadyExistsException;
import com.servletProject.librarySystem.repository.UserRepository;
import com.servletProject.librarySystem.repository.UserRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Transactional
    public void save(CreateUserEntityModel model) throws SQLException {

        Optional<UserEntity> userEntity = userRepository.findOneByMail(model.getMail());
        userEntity.ifPresent(entity -> {
            throw new ClientAlreadyExistsException("Client with this nick name already exists");
        });

        UserEntity entity = UserEntityConverter.toEntity(model);
        entity.setPermissionToOrder(true);
        entity.setLogin(false);

        Role role = createUserRole(entity);
        userRoleRepository.save(role);

        userRepository.save(entity);
    }

    private Role createUserRole(UserEntity entity) {
        Role role = new Role();
        role.setUserId(entity);
        role.setRole(Roles.USER.toString());
        return role;
    }

//    public UserEntityModel login(String nickName, String password) throws SQLException {
//        UserEntity user = null;
//        try {
//            TransactionManager.beginTransaction();
//            user = userDao.findUserByNickName(paramMap.get("nick_name"));
//            if (user == null) {
//                throw new ClientAlreadyExistsException("Client with this nick name does not exists");
//            } else if (!paramMap.get("password").trim().equals(user.getPassword())) {
//                throw new AuthorisationException("Password or nickname does not match");
//            } else {
//                List<String> roleList = userRoleDao.findUserRoleById(user.getId());
//                user.setRole(roleList);
//                return user;
//            }
//        } catch (SQLException | NullPointerException e) {
//            TransactionManager.rollBackTransaction();
//            throw e;
//        } finally {
//            TransactionManager.commitTransaction();
//        }
//    }
}