package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.domen.Role;
import com.servletProject.librarySystem.domen.UserEntity;
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

    @Transactional
    public void addUserRole(Long id, String newRole) {
        UserEntity user = userService.getUserIfExist(id);
        Role role = createRole(newRole);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Transactional
    public void removeUserRole(Long idUser, String removedRole) {
        if (isUserExist(idUser)) {
            UserEntity user = userService.getUserIfExist(idUser);
            Role role = createRole(removedRole);
            user.getRoles().remove(role);
            userRepository.save(user);
        } else throw new UserNotFoundException("User not found.");
    }

    private boolean isUserExist(long id) {
        return userService.isUserExist(id);
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