package com.servletProject.librarySystem.service.data;

import com.servletProject.librarySystem.converter.UserEntityConverter;
import com.servletProject.librarySystem.domen.Role;
import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.domen.dto.userEntity.CreateUserEntityModel;
import com.servletProject.librarySystem.exception.ClientAlreadyExistsException;
import com.servletProject.librarySystem.exception.UserNotFoundException;
import com.servletProject.librarySystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void checkIfTheUserExists(String email) {
        Optional<UserEntity> userEntity = userRepository.findOneByMail(email);
        userEntity.ifPresent(entity -> {
            throw new ClientAlreadyExistsException("Client with this nick name already exists");
        });
    }

    public void save(CreateUserEntityModel model) {
        checkIfTheUserExists(model.getMail());

        UserEntity entity = UserEntityConverter.toEntity(model);
        entity.setActive(true);
        entity.setPermission(true);
        entity.getRoles().add(Role.USER);

        userRepository.save(entity);
    }

    public boolean isUserExist(long id) {
        Optional<UserEntity> user = userRepository.findOneById(id);
        return user.isPresent();
    }

    public UserEntity getUserIfExist(long id) {
        Optional<UserEntity> user = userRepository.findOneById(id);
        if (user.isPresent()) {
            return user.get();
        } else throw new UserNotFoundException("The user you are looking for does not exist.");
    }

    public Long getUserIdByEmail(String email) {
        Optional<UserEntity> user = userRepository.findOneByMail(email);
        if (user.isPresent()) {
            return user.get().getId();
        } else throw new UserNotFoundException("The user you are looking for does not exist.");
    }

    public UserEntity getUserByEmail(String email) {
        Optional<UserEntity> user = userRepository.findOneByMail(email);
        if (user.isPresent()) {
            return user.get();
        } else throw new UserNotFoundException("The user you are looking for does not exist.");
    }

    public String getFullName(Long id) {
        if (isUserExist(id)) {
            return userRepository.findFullUserName(id);
        } else throw new UserNotFoundException("The user you are looking for does not exist.");
    }

    public UserEntity getUserByNickName(String nickName) {
        Optional<UserEntity> user = userRepository.findOneByNickName(nickName);
        if (user.isPresent()) {
            return user.get();
        } else throw new UserNotFoundException("The user you are looking for does not exist.");
    }
}
