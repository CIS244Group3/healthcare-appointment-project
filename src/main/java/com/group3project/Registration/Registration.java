package com.group3project.Registration;

public class Registration {
    protected String firstName;
    protected String lastName;
    protected String DOB;
    protected String address;
    protected int phone;
    protected String email;
    protected String securityQuestion;
    protected String username;
    protected String password;

    public Registration() {

    }

    public Registration(String firstName, String lastName, String DOB, String address, int phone, String email,
            String securityQuestion) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.securityQuestion = securityQuestion;

    }

    public Registration(String username) {
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password, String confirmedPassword) {
        boolean setPassword = false;

        while (!setPassword) {
            if (password.equals(confirmedPassword)) {
                this.password = password;
                setPassword = true;
            }
        }

    }

    public String getPassword() {
        return password;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    static Registration getRegistrationUsername(String username) throws UsernameNotFoundException {
        // TODO: connect to database to check if username exists
        String DB_username = "";

        if (username != DB_username) {
            throw new UsernameNotFoundException("Username does not exist");
        } else {
            return new Registration(username);
        }

    }

    static Registration getRegistrationEmail(String email) throws EmailNotFoundException {
        // TODO: connect to database to check if username exists
        String DB_email = "";

        if (email != DB_email) {
            throw new EmailNotFoundException("Email does not exist");
        } else {
            return new Registration(email);
        }

    }

}
