package com.servletProject.librarySystem.configuration;

import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.exception.UserNotFoundException;
import com.servletProject.librarySystem.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepo;

    public UserDetailsServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> applicationUser = userRepo.findOneByNickName(username);
        if (!applicationUser.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        UserEntity userEntity = applicationUser.get();
        return new User(
                userEntity.getNickName(),
                userEntity.getPassword(),
                userEntity.getRoles()
        );
    }
}
