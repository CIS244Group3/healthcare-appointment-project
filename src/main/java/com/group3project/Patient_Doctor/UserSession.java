package com.group3project.Patient_Doctor;

public class UserSession {
    private static String loggedInUsername; // represents username of the logged-in user

    public static String getLoggedInUsername() {
        return loggedInUsername;
    }

    public static void setLoggedInUsername(String username) {
        loggedInUsername = username;
    }
}
