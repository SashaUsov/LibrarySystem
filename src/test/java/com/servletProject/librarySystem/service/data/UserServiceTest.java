package com.servletProject.librarySystem.service.data;

import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.exception.ClientAlreadyExistsException;
import com.servletProject.librarySystem.exception.UserNotFoundException;
import com.servletProject.librarySystem.repository.UserRepository;
import com.servletProject.librarySystem.utils.HelperUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test(expected = ClientAlreadyExistsException.class)
    public void testSaveShouldThrowClientAlreadyExistsException() {
        var user = HelperUtil.getUser();
        var model = HelperUtil.getModel();

        when(userRepository.findOneByMail(anyString()))
                .thenReturn(Optional.of(user));
        userService.save(model);
    }

    @Test
    public void testSaveShouldDoNothing() {
        var model = HelperUtil.getModel();
        when(userRepository.findOneByMail(anyString()))
                .thenReturn(Optional.empty());
        when(userRepository.save(any(UserEntity.class)))
                .thenReturn(new UserEntity());

        userService.save(model);

        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    public void testGetUserIdByEmailShouldReturnUserId() {
        var expected = HelperUtil.getUser();
        when(userRepository.findOneByMail(anyString())).thenReturn(Optional.of(expected));
        long actual = userService.getUserIdByEmail(anyString());

        assertEquals(expected.getId(), actual);
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetUserIdByEmailShouldThrowUserNotFoundException() {
        when(userRepository.findOneByMail(anyString())).thenReturn(Optional.empty());
        userService.getUserIdByEmail(anyString());
    }

    @Test
    public void testGetUserByEmailShouldReturnUserEntity() {
        var expected = HelperUtil.getUser();
        when(userRepository.findOneByMail(anyString())).thenReturn(Optional.of(expected));
        var actual = userService.getUserByEmail(anyString());

        assertEquals(expected, actual);
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetUserByEmailShouldThrowUserNotFoundException() {
        when(userRepository.findOneByMail(anyString())).thenReturn(Optional.empty());
        userService.getUserByEmail(anyString());
    }

    @Test
    public void testGetFullNameShouldReturnFullUserName() {
        var user = HelperUtil.getUser();
        var expected = user.getFirstName() + " " + user.getLastName();
        when(userRepository.findOneById(anyLong()))
                .thenReturn(Optional.of(user));
        when(userRepository.findFullUserName(anyLong()))
                .thenReturn(user.getFirstName() + " " + user.getLastName());
        var actual = userService.getFullName(anyLong());

        assertEquals(expected, actual);
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetFullNameShouldThrowUserNotFoundException() {
        when(userRepository.findOneById(anyLong()))
                .thenReturn(Optional.empty());
        userService.getFullName(anyLong());
    }

    @Test
    public void testGetUserByNickNameShouldReturnUserEntity() {
        var expected = HelperUtil.getUser();
        when(userRepository.findOneByNickName(anyString()))
                .thenReturn(Optional.of(expected));
        var actual = userService.getUserByNickName(anyString());

        assertEquals(expected, actual);
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetUserByNickNameShouldThrowUserNotFoundException() {
        when(userRepository.findOneByNickName(anyString())).thenReturn(Optional.empty());
        userService.getUserByNickName(anyString());
    }
}
