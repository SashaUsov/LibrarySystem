package com.servletProject.librarySystem.dao.queries;

public class UserRoleDaoQueries {
    public static final String FIND_USER_ROLE_BY_ID = "SELECT user_role FROM user_role WHERE user_id= ?";
    public static final String SET_DEFAULT_ROLE = "INSERT INTO user_role (id, user_id, user_role) VALUES (?, ?, ?)";
    public static final String GET_NEXT_ID = "SELECT nextval('seq_user_role_id')";
}
