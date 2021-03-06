package com.servletProject.librarySystem.dao.queries;

public class BookDaoQueries {
    public static final String FIND_BOOK_ID_BY_BOOK_TITLE = "SELECT id FROM book_catalog WHERE book_title LIKE ?";

    public static final String GET_NEXT_BOOK_ID = "SELECT nextval('seq_book_id')";

    public static final String GET_NEXT_BOOK_COPY_ID = "SELECT nextval('seq_book_copy_id')";

    public static final String SAVE_BOOK =
            "INSERT INTO book_catalog (id, book_title, book_author, publication, genre) VALUES (?, ?, ?, ?, ?)";

    public static final String SAVE_BOOK_COPY = "INSERT INTO copies_of_books (id, id_book, book_condition) VALUES (?, ?, ?)";

    public static final String UPDATE_INCREMENT_BOOK_TOTAL_AMOUNT = "UPDATE book_catalog SET total_amount= total_amount+1 WHERE id= ?";

    public static final String UPDATE_DECREMENT_BOOK_TOTAL_AMOUNT = "UPDATE book_catalog SET total_amount= total_amount-1 WHERE id= ?";

    public static final String FIND_ALL_BOOK_FROM_CATALOG = "SELECT * FROM book_catalog";

    public static final String FIND_ALL_BOOK_COPY = "SELECT * FROM copies_of_books WHERE id_book= ?";

    public static final String FIND_ALL_BOOK_BY_BOOK_TITLE = "SELECT * FROM book_catalog WHERE book_title LIKE ?";

    public static final String FIND_ALL_BOOK_BY_BOOK_AUTHOR = "SELECT * FROM book_catalog WHERE book_author LIKE ?";

    public static final String FIND_ALL_BOOK_BY_BOOK_GENRE = "SELECT * FROM book_catalog WHERE genre LIKE ?";

    public static final String FIND_ALL_BOOK_COPY_BY_AVAILABILITY_AND_CONDITION =
            "SELECT * FROM copies_of_books WHERE availability= ? AND book_condition= ?";

    public static final String DELETE_BOOK_COPY_BY_ID_AND_AVAILABILITY = "DELETE FROM copies_of_books WHERE id= ? AND availability= ?";

    public static final String DELETE_FROM_ARCHIVE_BY_USER_ID_AND_COPY_ID =
            "DELETE FROM archive_book_usage WHERE id_copies_book= ?";

    public static final String FIND_BOOK_ID_BY_BOOK_COPY_ID = "SELECT id_book FROM copies_of_books WHERE id= ?";
}
