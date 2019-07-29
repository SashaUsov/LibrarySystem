package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.domen.Role;
import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.exception.PermissionToActionIsAbsentException;
import com.servletProject.librarySystem.repository.UserRepository;
import com.servletProject.librarySystem.service.data.UserService;
import com.servletProject.librarySystem.utils.HelperUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdminControllerServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private AdminControllerService adminControllerService;

    @Test(expected = PermissionToActionIsAbsentException.class)
    public void testAddUserRoleThrowPermissionToActionIsAbsentException() {
        when(userService.getUserByNickName(anyString())).thenReturn(HelperUtil.getUser());

        adminControllerService.addUserRole("user", "admin", Role.LIBRARIAN);

        verify(userService, times(1)).getUserByNickName(anyString());
    }

    @Test
    public void testAddUserRoleShouldDoNothing() {
        var user = HelperUtil.getUser();
        user.getRoles().add(Role.ADMIN);
        when(userService.getUserByNickName(anyString())).thenReturn(user);
        when(userRepository.save(any(UserEntity.class))).thenReturn(any(UserEntity.class));

        adminControllerService.addUserRole("user", "admin", Role.LIBRARIAN);

        verify(userService, times(2)).getUserByNickName(anyString());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test(expected = PermissionToActionIsAbsentException.class)
    public void testRemoveUserRoleThrowPermissionToActionIsAbsentException() {
        when(userService.getUserByNickName(anyString())).thenReturn(HelperUtil.getUser());

        adminControllerService.removeUserRole("user", "admin", Role.LIBRARIAN);

        verify(userService, times(1)).getUserByNickName(anyString());
    }

    @Test
    public void testRemoveUserRoleShouldDoNothing() {
        var user = HelperUtil.getUser();
        user.getRoles().add(Role.ADMIN);
        when(userService.getUserByNickName(anyString())).thenReturn(user);
        when(userRepository.save(any(UserEntity.class))).thenReturn(any(UserEntity.class));

        adminControllerService.removeUserRole("user", "admin", Role.ADMIN);

        verify(userService, times(2)).getUserByNickName(anyString());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }
}
