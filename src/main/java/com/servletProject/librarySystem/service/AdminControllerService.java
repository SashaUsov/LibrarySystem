package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.domen.Role;
import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.service.data.RoleService;
import com.servletProject.librarySystem.service.data.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminControllerService {

    private final UserService userService;
    private final RoleService roleService;

    public AdminControllerService(UserService userService,
                                  RoleService roleService
    ) {
        this.userService = userService;
        this.roleService = roleService;
    }

    public boolean isUserExist(long id) {
        return userService.isUserExist(id);
    }

    @Transactional
    public void addUserRole(long id, String newRole) {
        final UserEntity user = userService.getUserIfExist(id);
        Role role = createRole(newRole, user);
        roleService.save(role);
        user.getRoles().add(role);
        userService.save(user);
    }

    @Transactional
    public void removeUserRole(long idUser, String role) {
        Role userRole = roleService.findOneByUserIdAAndRole(idUser, role);
        roleService.delete(userRole);
        saveUserWithoutRole(idUser, role);
    }

    private void saveUserWithoutRole(long idUser, String role) {
        UserEntity user = userService.getUserIfExist(idUser);
        List<Role> roles = user.getRoles().stream()
                .filter(r -> !role.equals(r.getRole()))
                .collect(Collectors.toList());

        user.setRoles(roles);
        userService.save(user);
    }

    private Role createRole(String newRole, UserEntity user) {
        Role role = new Role();
        role.setRole(newRole);
        role.setUserId(user);
        return role;
    }
}