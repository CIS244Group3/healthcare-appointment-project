package com.group3project.Registration;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.Properties;
import java.util.Random;

import javax.mail.*;
import javax.mail.internet.*;

import com.group3project.Patient_Doctor.Patient;
import com.group3project.Utils.*;

public class RegistrationController implements Initializable {

    @FXML
    private ChoiceBox<String> cbGender;

    private String[] gender = { "Male", "Female", "Other" };

    @FXML
    private static Hyperlink login;

    @FXML
    private PasswordField pfConfirmPassword;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private DatePicker tfDOB;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfFirstName;

    @FXML
    private TextField tfLastName;

    @FXML
    private TextField tfPhoneNumber;

    @FXML
    private TextField tfUsername;

    @FXML
    private Button btnCreateAccount;

    private Patient patient;
    private long verificationCode;

    @FXML
    void confirmPassword(ActionEvent event) {

    }

    public void setUserName(String username) {
        this.tfUsername.setText(username);
    }

    private void clearField() {
        this.pfConfirmPassword.setText("");
        this.pfPassword.setText("");
        this.tfDOB.setValue(null);
        this.tfEmail.setText("");
        this.tfFirstName.setText("");
        this.tfLastName.setText("");
        this.tfPhoneNumber.setText("");
        this.tfUsername.setText("");
        this.cbGender.setValue(null);
    }

    Alert usernameExistsAlert = new Alert(AlertType.WARNING);

    private Scene loginScene;

    public void setLoginScene(Scene scene) {
        this.loginScene = scene;
    }

    private Scene oneTimeCodeScene;

    public void setOneTimeCodeScene(Scene scene) {
        this.oneTimeCodeScene = scene;
    }

    public void openLoginScene(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(loginScene);
        MainFx.updateUI(primaryStage, "Login Is Required", false);
        clearField();

    }

    public void oneTimeCodePopup(ActionEvent event, Patient patient) throws Exception {

        FXMLLoader fxmlLoaderOneTimeCode = new FXMLLoader(getClass().getResource("../../../fxml/OneTimeCode.fxml"));
        Parent oneTimeCodeRoot = fxmlLoaderOneTimeCode.load();
        OneTimeCodeController oneTimeCodeCont = (OneTimeCodeController) fxmlLoaderOneTimeCode.getController();
        this.oneTimeCodeScene = new Scene(oneTimeCodeRoot);
        oneTimeCodeCont.setOneTimeCode(this.verificationCode);
        oneTimeCodeCont.setUsername(patient.getUsername());
        openLoginScene(event);
        this.showUI(this.oneTimeCodeScene, StageStyle.DECORATED, "Enter One Time Code", false);

    }

    public void openNewScene(ActionEvent actionEvent, Scene scene, String title, boolean resizable) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        MainFx.updateUI(primaryStage, title, resizable);
    }

    private boolean anyFieldErrors() {
        String regExpn = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(pfPassword.getText());
        String phoneNumberRegEx = "\\d{3}-\\d{3}-\\d{4}";

        if (tfUsername.getText() == "" || tfFirstName.getText() == "" || tfLastName.getText() == ""
                || tfDOB.getValue() == null || tfEmail.getText() == "" || tfPhoneNumber.getText() == ""
                || pfPassword.getText() == "" || this.cbGender.getValue() == null
                || this.pfConfirmPassword.getText() == "") {

            showMessage("All fields are required");
            return true;
        }
        if (tfUsername.getText().length() < 8) {
            showMessage("Username must be at least 8 characters");
            return true;
        }
        if (!matcher.matches()) {
            System.out.println(pfPassword);
            System.out.println(pfConfirmPassword);
            showMessage(
                    "Password must be between 8 and 20 characters, include at least one capital letter, one number, and special character");
            return true;
        }
        if (!pfPassword.getText().matches(pfConfirmPassword.getText())) {
            System.out.println(pfPassword.getText());
            System.out.println(pfConfirmPassword.getText());
            showMessage("Passwords must match");
            return true;
        }
        if (!EmailValidator.getInstance().isValid(tfEmail.getText())) {
            showMessage("Email must be valid");
            return true;
        }
        if (!tfPhoneNumber.getText().matches(phoneNumberRegEx)) {
            showMessage(
                    "Phone number must be valid");
            return true;
        }

        return false;
    }

    @FXML
    void createUser(ActionEvent event) throws Exception {

        try {

            if (usernameOrEmailExists()) {
                showMessage("Username or Email Already Exists");
                clearField();
                return;
            }
            if (anyFieldErrors()) {
                return;
            }

            this.patient = createUser(tfFirstName.getText(),
                    tfLastName.getText(),
                    tfDOB.getValue(), tfUsername.getText(), pfPassword.getText(),
                    tfEmail.getText(),
                    tfPhoneNumber.getText(), cbGender.getValue());

        } catch (SQLException e) {
            print("SQL ERR: " + e.getMessage());
        }
        oneTimeCodePopup(event, this.patient);

    }

    @FXML
    void switchToLoginPage(ActionEvent event) {
        openLoginScene(event);
    }

    private boolean usernameOrEmailExists() throws SQLException {

        try {
            String sql = "SELECT * FROM patients WHERE username = ? OR email = ?";

            DbHelper dal = new DbHelper();

            Connection conn = dal.getConnection();

            PreparedStatement pStmt = conn.prepareStatement(sql);

            pStmt.setString(1, tfUsername.getText());
            pStmt.setString(2, tfEmail.getText());

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                conn.close();

                return true;
            } else {
                System.out.println("No record found");
                conn.close();
                return false;
            }

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return true;
    }

    private Patient createUser(String firstName, String lastName, LocalDate dob, String username, String password,
            String email, String phoneNumber, String gender) throws SQLException {
        String hashedPassword = ProjUtil.getSHA(password);
        Random rnd = new Random();
        long n = 100000 + rnd.nextInt(900000);
        this.verificationCode = n;

        try {
            String sql = "INSERT INTO patients (firstname, lastname, dob, username, password, email, phonenumber, gender, onetimecode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            DbHelper dal = new DbHelper();

            Connection conn = dal.getConnection();

            PreparedStatement pStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pStmt.setString(1, firstName);
            pStmt.setString(2, lastName);
            pStmt.setDate(3, java.sql.Date.valueOf(dob));
            pStmt.setString(4, username);
            pStmt.setString(5, hashedPassword);
            pStmt.setString(6, email);
            pStmt.setString(7, phoneNumber);
            pStmt.setString(8, gender);
            pStmt.setLong(9, this.verificationCode);

            int inserted = pStmt.executeUpdate();

            if (inserted == 1) {
                ResultSet generatedKeys = pStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);

                    Patient newUser = new Patient(id, firstName + " " + lastName, dob, username, email, phoneNumber,
                            gender);
                    System.out.print(newUser);
                    sendVerificationEmail();
                    showMessage("Account created, welcome! Please check your email to activate your account.");
                    return newUser;
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            } else {
                System.out.println("No record found");
                conn.close();
            }

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return null;
    }

    public void print(String str) {
        System.out.println(str);
    }

    private void showMessage(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }

    private void sendVerificationEmail() throws Exception {
        final String username = ProjUtil.getProperty("mail.name");
        final String password = ProjUtil.getProperty("mail.key");

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.debug", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // Create the email message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(tfEmail.getText()));
        message.setSubject("Welcome to Epoch Health Solutions! Here is your 6 digit one time code.");
        message.setText("Your one time code is: " + Long.toString(this.verificationCode));

        // Send the email
        Transport.send(message);
    }

    public void showUI(Scene scene, StageStyle stageStyle, String title, boolean resizable) {
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(title);

        stage.initStyle(stageStyle);

        stage.setResizable(resizable);

        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbGender.getItems().addAll(gender);
        clearField();
    }

}
