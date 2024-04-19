package com.group3project.Registration;

public class EmailExistsException extends Exception {
    public EmailExistsException(String message) {
        super(message);
    }

}
