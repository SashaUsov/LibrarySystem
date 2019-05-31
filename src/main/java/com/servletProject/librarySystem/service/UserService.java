package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.dao.UserDao;
import com.servletProject.librarySystem.dao.UserRoleDao;
import com.servletProject.librarySystem.dao.transaction.TransactionManager;
import com.servletProject.librarySystem.domen.Role;
import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.exception.ClientAlreadyExistsException;

import java.sql.SQLException;
import java.util.Map;

public class UserService {
    private UserDao userDao = new UserDao();
    private UserRoleDao userRoleDao = new UserRoleDao();

    public UserEntity save(Map<String, String> paramMap) throws SQLException {
        UserEntity user = null;
        try {
            TransactionManager.beginTransaction();
            user = userDao.findUserByNickName(paramMap.get("nick_name"));
            if (user != null) throw new ClientAlreadyExistsException("Client with this nick name already exists");

            user = userDao.save(paramMap);
            userRoleDao.setDefaultRole(user.getId());
            user.setRole(Role.USER.toString());
            return user;
        } catch (SQLException | NullPointerException e) {
            TransactionManager.rollBackTransaction();
            throw e;
        } finally {
            TransactionManager.commitTransaction();
        }
    }

}
