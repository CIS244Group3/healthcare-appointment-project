package com.group3project.Registration;

public class Login {
    private String username;
    private String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public static void resetPassword(String username, String newPassword, String confirmedPassword,
            String securityQuestion) throws PreviousPasswordDetectedException, SecurityQuestionDoesNotMatchException {
        boolean passwordReset = false;

        while (!passwordReset) {

            try {
                Registration user = Registration.getRegistrationUsername(username);
                if (newPassword.equals(user.getPassword())) {
                    throw new PreviousPasswordDetectedException("You have used this password in the past");
                }

                if (!securityQuestion.equals(user.getSecurityQuestion())) {
                    throw new SecurityQuestionDoesNotMatchException("Security answers do not match. Try again");
                }

                if (!newPassword.equals(user.getPassword()) && securityQuestion.equals(user.securityQuestion)) {
                    user.setPassword(newPassword, confirmedPassword);
                    passwordReset = true;
                }
            } catch (UsernameNotFoundException e) {
                e.printStackTrace();
            } catch (SecurityQuestionDoesNotMatchException e) {
                e.printStackTrace();
            }

        }

    }

    public static void getUsername(String email) {
        boolean usernameReset = false;

        while (!usernameReset) {
            try {
                Registration user = Registration.getRegistrationEmail(email);
            } catch (EmailNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

}
