@GenericGenerator(
        name = "seq_book_id",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @Parameter(name = "seq_book_id", value = "sequence"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_size", value = "1"),
        }
)

@GenericGenerator(
        name = "seq_user_id",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @Parameter(name = "seq_user_id", value = "sequence"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_size", value = "1"),
        }
)

@GenericGenerator(
        name = "seq_book_copy_id",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @Parameter(name = "seq_book_copy_id", value = "sequence"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_size", value = "1"),
        }
)

@GenericGenerator(
        name = "seq_book_order_id",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @Parameter(name = "seq_book_order_id", value = "sequence"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_size", value = "1"),
        }
)

@GenericGenerator(
        name = "seq_completed_orders_id",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @Parameter(name = "seq_completed_orders_id", value = "sequence"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_size", value = "1"),
        }
)

@GenericGenerator(
        name = "seq_archive_book_usage_id",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @Parameter(name = "seq_archive_book_usage_id", value = "sequence"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_size", value = "1"),
        }
)

@GenericGenerator(
        name = "seq_user_role_id",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @Parameter(name = "seq_user_role_id", value = "sequence"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_size", value = "1"),
        }
)
package com.servletProject.librarySystem.domen;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;