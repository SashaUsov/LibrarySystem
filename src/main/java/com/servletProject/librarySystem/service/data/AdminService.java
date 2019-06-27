package com.servletProject.librarySystem.service.data;

import com.servletProject.librarySystem.domen.Role;
import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.exception.UserNotFoundException;
import com.servletProject.librarySystem.exception.UserRoleNotFoundException;
import com.servletProject.librarySystem.repository.UserRepository;
import com.servletProject.librarySystem.repository.UserRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public AdminService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public boolean isUserExist(long id) {
        Optional<UserEntity> user = userRepository.findOneById(id);
        return user.isPresent();
    }

    @Transactional
    public void addUserRole(long id, String newRole) {
        Optional<UserEntity> user = userRepository.findOneById(id);
        if(user.isPresent()) {
            Role role = new Role();
            role.setRole(newRole);
            role.setUserId(user.get());
            userRoleRepository.save(role);
        } else throw new UserNotFoundException("User not found :(");
    }

    @Transactional
    public void removeUserRole(long idUser, String role) {
        Optional<Role> userRole = userRoleRepository.findOneByUserIdAAndRole(idUser, role);
        if(userRole.isPresent()) {
            userRoleRepository.delete(userRole.get());
        } else throw new UserRoleNotFoundException("User does not have this role.");
    }
}