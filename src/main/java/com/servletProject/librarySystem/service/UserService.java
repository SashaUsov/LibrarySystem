package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.dao.UserDao;
import com.servletProject.librarySystem.dao.UserRoleDao;
import com.servletProject.librarySystem.dao.transactionManager.TransactionManager;
import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.exception.AuthorisationException;
import com.servletProject.librarySystem.exception.ClientAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
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
            List<String> roleList = userRoleDao.findUserRoleById(user.getId());
            user.setRole(roleList);
            return user;
        } catch (SQLException | NullPointerException e) {
            TransactionManager.rollBackTransaction();
            throw e;
        } finally {
            TransactionManager.commitTransaction();
        }
    }

    public UserEntity login(Map<String, String> paramMap) throws SQLException {
        UserEntity user = null;
        try {
            TransactionManager.beginTransaction();
            user = userDao.findUserByNickName(paramMap.get("nick_name"));
            if (user == null) {
                throw new ClientAlreadyExistsException("Client with this nick name does not exists");
            } else if (!paramMap.get("password").trim().equals(user.getPassword())) {
                throw new AuthorisationException("Password or nickname does not match");
            } else {
                List<String> roleList = userRoleDao.findUserRoleById(user.getId());
                user.setRole(roleList);
                return user;
            }
        } catch (SQLException | NullPointerException e) {
            TransactionManager.rollBackTransaction();
            throw e;
        } finally {
            TransactionManager.commitTransaction();
        }
    }
}