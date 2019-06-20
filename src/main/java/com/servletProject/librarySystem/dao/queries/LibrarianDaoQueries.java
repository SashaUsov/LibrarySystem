package com.servletProject.librarySystem.dao.queries;

public class LibrarianDaoQueries {
    public static final String DELETE_BOOK_COPY_BY_COPY_ID = "DELETE FROM book_order WHERE book_copy_id= ?";

    public static final String GET_NEXT_ID = "SELECT nextval('seq_completed_orders_id')";

    public static final String DELETE_ORDER_BY_COPY_ID_AND_USER_ID = "DELETE FROM book_order WHERE book_copy_id= ? AND user_id= ?";

    public static final String GIVE_BOOK_TO_THE_USER = "INSERT INTO completed_orders (id, id_reader, id_librarian, id_book)" +
            "VALUES (?, ?, ?, ?)";

    public static final String FIND_ALL_COMPLETED_ORDERS_BY_READER_ID = "SELECT * FROM completed_orders WHERE id_reader= ?";

    public static final String FIND_ALL_COMPLETED_ORDERS = "SELECT * FROM completed_orders";

    public static final String PUT_THE_BOOK_IN_USAGE_ARCHIVE = "INSERT INTO archive_book_usage (id, id_reader, " +
            "id_copies_book, book_condition) VALUES (?, ?, ?, ?)";

    public static final String GET_NEXT_USAGE_ID = "SELECT nextval('seq_archive_book_usage_id')";

    public static final String DELETE_COMPLETED_ORDER_BY_COPY_ID = "DELETE FROM completed_orders WHERE id_book= ?";

    public static final String UPDATE_BOOK_COPY_INFO = "UPDATE copies_of_books SET availability= ?, book_condition= ? WHERE id= ?";
}
