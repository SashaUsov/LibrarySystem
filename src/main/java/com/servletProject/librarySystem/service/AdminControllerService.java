package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.domen.Role;
import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.exception.PermissionToActionIsAbsentException;
import com.servletProject.librarySystem.exception.UserNotFoundException;
import com.servletProject.librarySystem.repository.UserRepository;
import com.servletProject.librarySystem.service.data.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminControllerService {

    private final UserService userService;
    private final UserRepository userRepository;

    public AdminControllerService(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public void addUserRole(String nickName,  String admin, String newRole) {
        if (isAdmin(admin)) {
            UserEntity user = userService.getUserByNickName(nickName);
            Role role = createRole(newRole);
            user.getRoles().add(role);
            userRepository.save(user);
        } else throw new PermissionToActionIsAbsentException("You do not have permission to grant a role to the user.");
    }

    public void removeUserRole(String nickName,  String admin, String removedRole) {
        if (isAdmin(admin)) {
            UserEntity user = userService.getUserByNickName(nickName);
            Role role = createRole(removedRole);
            user.getRoles().remove(role);
            userRepository.save(user);
        } else throw new PermissionToActionIsAbsentException("You do not have permission to revoke the role from the user.");
    }

    private boolean isAdmin(String admin) {
        UserEntity adminEntity = userService.getUserByNickName(admin);
        return adminEntity.getRoles().contains(Role.ADMIN);
    }



    private Role createRole(String newRole) {
        Role role = null;
        switch (newRole) {
            case "USER":
                role = Role.USER;
                break;
            case "ADMIN":
                role = Role.ADMIN;
                break;
            case "LIBRARIAN":
                role = Role.LIBRARIAN;
                break;
        }
        return role;
    }
}
