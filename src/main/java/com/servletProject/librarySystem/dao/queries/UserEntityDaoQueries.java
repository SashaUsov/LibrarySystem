package com.servletProject.librarySystem.dao.queries;

public class UserEntityDaoQueries {
    public static final String FIND_USER_BY_NICK_NAME = "SELECT * FROM user_entity where nick_name= ?";

    public static final String FIND_USER_BY_ID = "SELECT * FROM user_entity WHERE id= ?";

    public static final String FIND_USER_BY_EMAIL = "SELECT * FROM user_entity WHERE mail= ?";

    public static final String SAVE_USER = "INSERT INTO user_entity (id, first_name, last_name, nick_name," +
                                            "password, mail, address) VALUES (?, ?, ?, ?, ?, ?, ?)";

    public static final String GET_NEXT_ID = "SELECT nextval('seq_user_id')";

    public static final String FIND_FULL_USER_NAME_BY_ID = "SELECT first_name, last_name FROM user_entity WHERE id= ?";
}
