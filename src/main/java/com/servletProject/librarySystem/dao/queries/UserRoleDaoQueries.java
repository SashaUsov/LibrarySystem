package com.servletProject.librarySystem.dao.queries;

public class UserRoleDaoQueries {
    public static final String FIND_USER_ROLE_BY_ID = "SELECT role FROM user_role WHERE user_id= ?";

    public static final String SET_USER_ROLE = "INSERT INTO user_role (id, user_id, role) VALUES (?, ?, ?)";

    public static final String GET_NEXT_ID = "SELECT nextval('seq_user_role_id')";

    public static final String DELETE_BY_USER_ID_AND_ROLE = "DELETE FROM user_role r" +
                                                            "WHERE r.user_id= ? AND r.role= ?;";
}
