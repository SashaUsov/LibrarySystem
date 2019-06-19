package com.servletProject.librarySystem.dao.queries;

public class LibrarianDaoQueries {
    public static final String DELETE_BOOK_COPY_BY_COPY_ID = "DELETE FROM book_order WHERE book_copy_id= ?";

    public static final String GET_NEXT_ID = "SELECT nextval('seq_completed_orders_id')";

    public static final String FIND_ORDER_BY_COPY_ID = "SELECT * FROM book_order WHERE book_copy_id= ?";

    public static final String DELETE_ORDER_BY_COPY_ID_AND_USER_ID = "DELETE FROM book_order WHERE book_copy_id= ? AND user_id= ?";

    public static final String GIVE_BOOK_TO_THE_USER = "INSERT INTO completed_orders (id, id_reader, id_librarian, id_book)" +
                                                       "VALUES (?, ?, ?, ?)";
}
