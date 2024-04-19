package com.group3project.Registration;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
// import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

    @FXML
    void confirmPassword(ActionEvent event) {

    }

    Alert usernameExistsAlert = new Alert(AlertType.WARNING);

    private Scene loginScene;

    public void setLoginScene(Scene scene) {
        loginScene = scene;
    }

    private Scene homepageScene;

    public void setHomepageScene(Scene scene) {
        homepageScene = scene;
    }

    public void openLoginScene(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(loginScene);
        MainFx.updateUI(primaryStage, "Login Is Required", false);
    }

    public void openNewScene(ActionEvent actionEvent, Scene scene, String title, boolean resizable) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        MainFx.updateUI(primaryStage, title, resizable);
    }

    @FXML
    void createUser(ActionEvent event) {

        try {

            if (usernameExists(tfUsername.getText())) {
                System.out.println("user: " + tfUsername.getText());
                // Alert usernameExistsAlert = new Alert(Alert.AlertType.WARNING);
                // usernameExistsAlert.setTitle("ERROR");
                // usernameExistsAlert.setHeaderText("ERROR");
                // usernameExistsAlert.setContentText("User exists. Try another user.");
                // usernameExistsAlert.showAndWait();
                print("User already exists");
                tfUsername.setText("");

                return;
            }

            if (emailExists(tfEmail.getText())) {
                // Alert emailExistsAlert = new Alert(Alert.AlertType.WARNING);
                // emailExistsAlert.setTitle("ERROR");
                // emailExistsAlert.setHeaderText("ERROR");
                // emailExistsAlert.setContentText("This email already has an account. Try
                // another email.");
                // emailExistsAlert.showAndWait();
                print("Email already exists");
                tfEmail.setText("");
                return;
            }

            print("Username nor email found, creating user...");

            createUser(tfUsername.getText(),
                    tfFirstName.getText(),
                    tfLastName.getText(),
                    tfDOB.toString(),
                    tfEmail.getText(),
                    pfPassword.getText(),
                    tfPhoneNumber.getText());

        } catch (SQLException e) {
            print("SQL ERR: " + e.getMessage());
        }
        openNewScene(event, this.homepageScene, "Homepage", false);

    }

    @FXML
    void switchToLoginPage(ActionEvent event) {
        openLoginScene(event);
    }

    // private void printFromDB() throws SQLException {

    // Connection conn = null;
    // try {
    // String sql = "SELECT * FROM patients;";

    // DbHelper dal = new DbHelper();

    // conn = dal.getConnection();

    // ResultSet rs = dal.executeQuery(conn, sql);

    // while (rs.next()) {
    // print(rs.getString("username"));
    // print(rs.getString("firstname"));
    // print(rs.getString("lastname"));
    // print(rs.getString("DOB"));
    // print(rs.getString("Phone"));
    // print(rs.getString("email"));
    // print(rs.getString("userpassword"));

    // }

    // conn.close();

    // } catch (Exception ex) {
    // System.out.println("Error: " + ex.getMessage());
    // } finally {
    // if (conn != null) {
    // conn.close();
    // }
    // }

    // }

    private static boolean usernameExists(String username) throws SQLException {

        Connection conn = null;

        try {
            String sql = "SELECT * FROM patients WHERE username = ' " + username + " ' ";

            DbHelper dbHelper = new DbHelper();

            conn = dbHelper.getConnection();

            ResultSet rs = dbHelper.executeQuery(conn, sql);

            // if (rs.last()) {
            // return true;
            // }

            System.out.println("usernameexists.Before_last()");
            boolean lastWorked = rs.last();
            System.out.println("usernameexists.After_last()");

            if (lastWorked) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return false;

    }

    private static boolean emailExists(String email) throws SQLException {

        Connection conn = null;

        try {
            String sql = "SELECT * FROM patients WHERE email = ' " + email + " ' ";

            DbHelper dbHelper = new DbHelper();

            conn = dbHelper.getConnection();

            ResultSet rs = dbHelper.executeQuery(conn, sql);

            System.out.println("emailexists.Before_last()");
            boolean lastWorked = rs.last();
            System.out.println("emailexists.After_last()");

            if (lastWorked) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return false;

    }

    private static void createUser(String userName, String firstName, String lastName, String dob, String email,
            String password, String phone) throws SQLException {

        Connection conn = null;

        try {
            String sql = "INSERT INTO patients (username, firstname, lastname, dob, email, userpassword, phone)" +
                    "VALUES (' " + userName + " ', ' "
                    + firstName
                    + " ', ' " + lastName
                    + " ', ' " + dob
                    + " ', ' " + email
                    + " ', ' " + password
                    + " ', ' " + phone + " ' );";
            // + " ' , " +
            // + " ' " + gender + " ' ); ";

            DbHelper dbHelper = new DbHelper();

            conn = dbHelper.getConnection();

            dbHelper.executeQuery(conn, sql);

            conn.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void print(String str) {
        System.out.println(str);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbGender.getItems().addAll(gender);
    }

}
