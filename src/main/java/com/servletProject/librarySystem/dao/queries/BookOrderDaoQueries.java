package com.servletProject.librarySystem.dao.queries;

public class BookOrderDaoQueries {

    public static final String GET_NEXT_ORDER_ID = "SELECT nextval('seq_book_order_id')";

    public static final String FIND_AVAILABILITY_OF_BOOK_COPY_BY_COPY_ID =
            "SELECT availability FROM copies_of_books WHERE id= ?";

    public static final String UPDATE_AVAILABILITY_OF_BOOK_COPY =
            "UPDATE copies_of_books SET availability= ? WHERE id= ?";

    public static final String FIND_BOOK_COPY_BY_ID_AND_AVAILABILITY =
            "SELECT * FROM copies_of_books WHERE id= ? AND availability= ?";
}
