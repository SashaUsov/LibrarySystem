package com.servletProject.librarySystem.service;

import com.servletProject.librarySystem.converter.UserEntityConverter;
import com.servletProject.librarySystem.domen.Role;
import com.servletProject.librarySystem.domen.Roles;
import com.servletProject.librarySystem.domen.UserEntity;
import com.servletProject.librarySystem.domen.dto.userEntity.CreateUserEntityModel;
import com.servletProject.librarySystem.service.data.RoleService;
import com.servletProject.librarySystem.service.data.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserControllerService {
    private final RoleService roleService;
    private final UserService userService;

    public UserControllerService(RoleService roleService,
                                 UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Transactional
    public void save(CreateUserEntityModel model) {

        userService.checkIfTheUserExists(model.getMail());

        UserEntity entity = UserEntityConverter.toEntity(model);
        entity.setPermissionToOrder(true);
        entity.setLogin(false);

        Role role = createUserRole(entity);
        roleService.save(role);

        userService.save(entity);
    }

    private Role createUserRole(UserEntity entity) {
        Role role = new Role();
        role.setUserId(entity);
        role.setRole(Roles.USER.toString());
        return role;
    }

//    public UserEntityModel login(String nickName, String password) throws SQLException {
//        UserEntity user = null;
//        try {
//            TransactionManager.beginTransaction();
//            user = userDao.findUserByNickName(paramMap.get("nick_name"));
//            if (user == null) {
//                throw new ClientAlreadyExistsException("Client with this nick name does not exists");
//            } else if (!paramMap.get("password").trim().equals(user.getPassword())) {
//                throw new AuthorisationException("Password or nickname does not match");
//            } else {
//                List<String> roleList = userRoleDao.findUserRoleById(user.getId());
//                user.setRole(roleList);
//                return user;
//            }
//        } catch (SQLException | NullPointerException e) {
//            TransactionManager.rollBackTransaction();
//            throw e;
//        } finally {
//            TransactionManager.commitTransaction();
//        }
//    }
}