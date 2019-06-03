package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.dao.UserDao;
import com.servletProject.librarySystem.dao.UserRoleDao;

import java.sql.SQLException;
import java.util.List;

public class AdminService {
    private UserDao userDao = new UserDao();
    private UserRoleDao userRoleDao = new UserRoleDao();

    public boolean isUserExist(long id) throws SQLException {
        return userDao.findUserById(id) != null;
    }

    public void addUserRole(long id, String newRole) throws SQLException {
        List<String> userRoleById = userRoleDao.findUserRoleById(id);
        for (String role : userRoleById) {
            if (newRole.equals(role)) break;
        }
        userRoleDao.setUserRole(id, newRole);
    }

    public void removeUserRole(long id, String role) throws SQLException {
        List<String> userRoleById = userRoleDao.findUserRoleById(id);
        for (String r : userRoleById) {
            if (role.equals(r)) {
                userRoleDao.deleteRoleByUserIdAndRole(id, role);
            }
        }
    }
}
