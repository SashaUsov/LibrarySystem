package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.dao.UserDao;
import com.servletProject.librarySystem.dao.UserRoleDao;
import com.servletProject.librarySystem.dao.transactionManager.TransactionManager;
import com.servletProject.librarySystem.domen.UserEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class AdminService {
    private UserDao userDao = new UserDao();
    private UserRoleDao userRoleDao = new UserRoleDao();

    public boolean isUserExist(long id) throws SQLException {
        UserEntity user = null;
        try {
            TransactionManager.beginTransaction();
            user = userDao.findUserById(id);
            return user != null;
        } catch (SQLException e) {
            TransactionManager.rollBackTransaction();
            throw e;
        } finally {
            TransactionManager.commitTransaction();
        }
    }

    public void addUserRole(long id, String newRole) throws SQLException {
        try {
            TransactionManager.beginTransaction();
            List<String> userRoleById = userRoleDao.findUserRoleById(id);
            for (String role : userRoleById) {
                if (newRole.equals(role)) break;
            }
            userRoleDao.setUserRole(id, newRole);
        } catch (SQLException e) {
            TransactionManager.rollBackTransaction();
            throw e;
        } finally {
            TransactionManager.commitTransaction();
        }
    }

    public void removeUserRole(long id, String role) throws SQLException {
        try {
            TransactionManager.beginTransaction();
            List<String> userRoleById = userRoleDao.findUserRoleById(id);
            for (String r : userRoleById) {
                if (role.equals(r)) {
                    userRoleDao.deleteRoleByUserIdAndRole(id, role);
                }
            }
        } catch (SQLException e) {
            TransactionManager.rollBackTransaction();
            throw e;
        } finally {
            TransactionManager.commitTransaction();
        }
    }
}