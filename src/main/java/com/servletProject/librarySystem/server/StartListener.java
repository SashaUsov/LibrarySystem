package com.servletProject.librarySystem.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class StartListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LiquibaseRun.runLiquibase();
    }
}
