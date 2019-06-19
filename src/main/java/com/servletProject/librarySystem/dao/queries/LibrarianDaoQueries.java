package com.servletProject.librarySystem.dao.queries;

public class LibrarianDaoQueries {
    public static final String DELETE_BOOK_COPY_BY_COPY_ID = "DELETE FROM book_order WHERE book_copy_id= ?";

    public static final String GET_NEXT_ID = "SELECT nextval('')";

    public static final String FIND_ORDER_BY_COPY_ID = "SELECT * FROM book_order WHERE book_copy_id= ?";
}
