package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.domen.Role;
import com.servletProject.librarySystem.exception.PermissionToActionIsAbsentException;
import com.servletProject.librarySystem.repository.UserRepository;
import com.servletProject.librarySystem.service.data.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdminControllerService {

    private final UserService userService;
    private final UserRepository userRepository;

    public AdminControllerService(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public void addUserRole(String nickName, String admin, Role role) {
        if (isAdmin(admin)) {
            var user = userService.getUserByNickName(nickName);
            user.getRoles().add(role);
            userRepository.save(user);
            log.info("Role : \"" + role.toString() + "\" granted to user with id= " + user.getId() + " .");
        } else throw new PermissionToActionIsAbsentException("You do not have permission to grant a role to the user.");
    }

    public void removeUserRole(String nickName, String admin, Role removedRole) {
        if (isAdmin(admin)) {
            var user = userService.getUserByNickName(nickName);
            user.getRoles().remove(removedRole);
            userRepository.save(user);
            log.info("Role : \"" + removedRole.toString() + "\" revoked from the user with id= " + user.getId() + " .");
        } else throw new PermissionToActionIsAbsentException("You do not have permission to revoke the role from the user.");
    }

    private boolean isAdmin(String admin) {
        var adminEntity = userService.getUserByNickName(admin);
        return adminEntity.getRoles().contains(Role.ADMIN);
    }
}
