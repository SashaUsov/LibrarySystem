package com.servletProject.librarySystem.service.data;

import com.servletProject.librarySystem.converter.UserEntityConverter;
import com.servletProject.librarySystem.domen.Role;
import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.domen.dto.userEntity.CreateUserEntityModel;
import com.servletProject.librarySystem.exception.ClientAlreadyExistsException;
import com.servletProject.librarySystem.exception.UserNotFoundException;
import com.servletProject.librarySystem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(CreateUserEntityModel model) {
        checkIfTheUserExists(model.getMail());

        var entity = UserEntityConverter.toEntity(model);
        entity.setActive(true);
        entity.setPermission(true);
        entity.getRoles().add(Role.USER);

        var userEntity = userRepository.save(entity);
        log.info("New user created. id=" + userEntity.getId() + " .");
    }

    public Long getUserIdByEmail(String email) {
        var user = userRepository.findOneByMail(email);
        if (user.isPresent()) {
            return user.get().getId();
        } else throw new UserNotFoundException("The user you are looking for does not exist.");
    }

    public UserEntity getUserByEmail(String email) {
        var user = userRepository.findOneByMail(email);
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
        var user = userRepository.findOneByNickName(nickName);
        if (user.isPresent()) {
            return user.get();
        } else throw new UserNotFoundException("The user you are looking for does not exist.");
    }

    private void checkIfTheUserExists(String email) {
        var userEntity = userRepository.findOneByMail(email);
        userEntity.ifPresent(entity -> {
            throw new ClientAlreadyExistsException("Client with this nick name already exists");
        });
    }

    private boolean isUserExist(long id) {
        var user = userRepository.findOneById(id);
        return user.isPresent();
    }
}
