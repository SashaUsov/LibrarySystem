package com.servletProject.librarySystem.dao.transactionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WrapConnection implements AutoCloseable{
    private Connection connection;

    public WrapConnection(Connection connection){
        this.connection = connection;
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    Connection getRealConnection(){
        return connection;
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    public void close() {

    }
}
