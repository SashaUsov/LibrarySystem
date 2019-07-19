package com.servletProject.librarySystem.service.data;

import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.domen.dto.userEntity.CreateUserEntityModel;
import com.servletProject.librarySystem.exception.ClientAlreadyExistsException;
import com.servletProject.librarySystem.exception.UserNotFoundException;
import com.servletProject.librarySystem.repository.UserRepository;
import com.servletProject.librarySystem.utils.HelperUtil;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test(expected = ClientAlreadyExistsException.class)
    public void shouldThrowClientAlreadyExistsExceptionWhenTrySaveUserWithEmailWhichAlreadyExist() {
        UserEntity user = HelperUtil.getUser();
        CreateUserEntityModel model = HelperUtil.getModel();

        when(userRepository.findOneByMail(anyString()))
                .thenReturn(Optional.of(user));
        userService.save(model);
    }

    @Test
    public void shouldDoNothingWhenSaveCreateUserEntityModel() {
        CreateUserEntityModel model = HelperUtil.getModel();
        when(userRepository.findOneByMail(anyString()))
                .thenReturn(Optional.empty());
        when(userRepository.save(any(UserEntity.class)))
                .thenReturn(new UserEntity());

        userService.save(model);

        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    public void shouldReturnUserIdWhenGetUserIdByEmailTry() {
        UserEntity expected = HelperUtil.getUser();
        when(userRepository.findOneByMail(anyString())).thenReturn(Optional.of(expected));
        long actual = userService.getUserIdByEmail(anyString());

        assertEquals(expected.getId(), actual);
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldThrowUserNotFoundExceptionWhenGetUserIdByEmailAndEmailNotExistInDb() {
        when(userRepository.findOneByMail(anyString())).thenReturn(Optional.empty());
        userService.getUserIdByEmail(anyString());
    }

    @Test
    public void shouldReturnUserEntityWhenGetUserByEmail() {
        UserEntity expected = HelperUtil.getUser();
        when(userRepository.findOneByMail(anyString())).thenReturn(Optional.of(expected));
        UserEntity actual = userService.getUserByEmail(anyString());

        assertEquals(expected, actual);
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldThrowUserNotFoundExceptionWhenGetUserByEmailAndEmailNotExistInDb() {
        when(userRepository.findOneByMail(anyString())).thenReturn(Optional.empty());
        userService.getUserByEmail(anyString());
    }

    @Test
    public void shouldReturnFullUserNameWhenGetFullNameTry() {
        UserEntity user = HelperUtil.getUser();
        String expected = user.getFirstName() + " " + user.getLastName();
        when(userRepository.findOneById(anyLong()))
                .thenReturn(Optional.of(user));
        when(userRepository.findFullUserName(anyLong()))
                .thenReturn(user.getFirstName() + " " + user.getLastName());
        String actual = userService.getFullName(anyLong());

        assertEquals(expected, actual);
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldThrowUserNotFoundExceptionWhenGetFullNameAddIdIsNotExist() {
        when(userRepository.findOneById(anyLong()))
                .thenReturn(Optional.empty());
        userService.getFullName(anyLong());
    }

    @Test
    public void shouldReturnUserEntityWhenGetUserByNickName() {
        UserEntity expected = HelperUtil.getUser();
        when(userRepository.findOneByNickName(anyString()))
                .thenReturn(Optional.of(expected));
        UserEntity actual = userService.getUserByNickName(anyString());

        assertEquals(expected, actual);
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldThrowUserNotFoundExceptionWhenGetUserByNickNameAndNickNameIsNotExistInDb() {
        when(userRepository.findOneByNickName(anyString())).thenReturn(Optional.empty());
        userService.getUserByNickName(anyString());
    }
}
