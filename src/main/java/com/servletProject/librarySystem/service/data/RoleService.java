package com.servletProject.librarySystem.service.data;

import com.servletProject.librarySystem.domen.Role;
import com.servletProject.librarySystem.exception.UserNotFoundException;
import com.servletProject.librarySystem.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private final UserRoleRepository userRoleRepository;

    public RoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public void save(Role role) {
        userRoleRepository.save(role);
    }

    public Role findOneByUserIdAAndRole(long idUser, String role) {
        Optional<Role> useRole = userRoleRepository.findOneByUserIdAAndRole(idUser, role);
        if (useRole.isPresent()) {
            return useRole.get();
        } else throw new UserNotFoundException("The role you are looking for does not exist.");
    }

    public void delete(Role role) {
        userRoleRepository.delete(role);
    }
}
