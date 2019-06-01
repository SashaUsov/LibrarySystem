package com.servletProject.librarySystem.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoadSQLRequestUtil {

    public static void loadPropertyFile(Properties sqlUserRequest, String path) {
        try (FileInputStream in = new FileInputStream(path)) {
            sqlUserRequest  = new Properties();
            sqlUserRequest.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
