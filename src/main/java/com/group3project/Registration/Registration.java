package com.group3project.Registration;

public class Registration {
    protected String firstName;
    protected String lastName;
    protected String DOB;
    protected String phone;
    protected String email;
    protected String username;
    protected String password;
    protected String confirmPassword;

    public Registration() {

    }

    public Registration(String username, String firstName, String lastName, String DOB, String phone, String email,
            String password, String confirmPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;

    }

    public Registration(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDOB() {
        return DOB;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        boolean setPassword = false;

        while (!setPassword) {
            if (this.password.equals(this.confirmPassword)) {
                this.password = password;
                setPassword = true;
            }
        }

    }

    public String getPassword() {
        return password;
    }

    static Registration getRegistrationUsername(String username) throws UsernameExistsException {
        // TODO: connect to database to check if username exists
        String DB_username = "";

        if (username != DB_username) {
            return new Registration(username);
        } else {
            throw new UsernameExistsException("Username already exists");
        }

    }

    static Registration getRegistrationEmail(String email) throws EmailExistsException {
        // TODO: connect to database to check if username exists
        String DB_email = "";

        if (email != DB_email) {
            return new Registration(email);

        } else {
            throw new EmailExistsException("Email already exists");

        }

    }

}
