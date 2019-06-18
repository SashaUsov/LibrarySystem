package com.servletProject.librarySystem.dao.queries;

public class BookOrderDaoQueries {

    public static final String GET_NEXT_ORDER_ID = "SELECT nextval('seq_book_order_id')";

    public static final String FIND_AVAILABILITY_OF_BOOK_COPY_BY_COPY_ID =
            "SELECT availability FROM copies_of_books WHERE id= ?";

    public static final String UPDATE_AVAILABILITY_OF_BOOK_COPY =
            "UPDATE copies_of_books SET availability= ? WHERE id= ?";

    public static final String FIND_BOOK_COPY_BY_ID_AND_AVAILABILITY =
            "SELECT * FROM copies_of_books WHERE id= ? AND availability= ?";

    public static final String RESERVE_BOOK_COPY = "INSERT INTO book_order (id, user_id, book_copy_id) VALUES (?, ?, ?)";

    public static final String FIND_ALL_RESERVED_BOOKS_COPY_BY_READER_ID = "SELECT book_copy_id FROM book_order WHERE user_id= ?";

    public static final String FIND_ALL_BOOKS_ID_BY_COPY_ID = "SELECT id_book FROM copies_of_books WHERE id IN ?";

    public static final String FIND_BOOK_BY_ID = "SELECT * FROM book_catalog WHERE id= ?";
}
