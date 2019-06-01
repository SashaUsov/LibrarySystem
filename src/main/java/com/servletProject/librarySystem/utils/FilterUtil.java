package com.servletProject.librarySystem.utils;

import java.util.List;

public class FilterUtil {

    public static long getAccessLevel(List<String> roles) {
        long accessLevel = 0;
        for (String role : roles) {
            switch (role) {
                case "USER" : accessLevel++;
                    break;
                case "ADMIN" : accessLevel++;
                    break;
                case "LIBRARIAN" : accessLevel++;
                    break;
            }
        }
        return accessLevel;
    }
}
