package com.servletProject.librarySystem.server;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class LiquibaseRun {
    private static Properties dbProps;

    private static void loadPropertyFile() {
        try (InputStream in = LiquibaseRun.class.getClassLoader().getResourceAsStream("db.properties")) {
            dbProps  = new Properties();
            dbProps.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public static void runLiquibase() {

        loadPropertyFile();

        Liquibase liquibase = null;
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(dbProps.getProperty("url"),
                                            dbProps.getProperty("username"),
                                            dbProps.getProperty("password"));

            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(c));
            liquibase = new Liquibase(dbProps.getProperty("changelog"), new ClassLoaderResourceAccessor(), database);
            liquibase.update("main");
        } finally {
            if (c != null) {
                c.rollback();
                c.close();
            }
        }
    }
}
