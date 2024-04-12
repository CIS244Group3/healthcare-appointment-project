package com.group3project.Registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.group3project.Utils.*;

public class RegistrationController {

    @FXML
    private static Hyperlink login;

    @FXML
    private static PasswordField pfConfirmPassword;

    @FXML
    private static PasswordField pfPassword;

    @FXML
    private DatePicker tfDOB;

    @FXML
    private static TextField tfEmail;

    @FXML
    private static TextField tfFirstName;

    @FXML
    private static TextField tfLastName;

    @FXML
    private static TextField tfPhoneNumber;

    @FXML
    private static TextField tfUsername;

    @FXML
    void confirmPassword(ActionEvent event) {

    }

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
        // try {
        // if (doesUserNameExist(tfUsername.getText())) {
        // print("We found the user");
        // return;
        // }

        // print("User name not found, creating username...");

        // createUser(tfUsername.getText(), tfFirstName.getText(), tfLastName.getText(),
        // tfDOB.getText(),
        // tfEmail.getText(), pfPassword.getText(), tfPhoneNumber.getText());

        // } catch (SQLException e) {
        // print("SQL ERR: " + e.getMessage());
        // }
        openNewScene(event, this.homepageScene, "Homepage", false);

    }

    @FXML
    void switchToLoginPage(ActionEvent event) {
        openLoginScene(event);
    }

    private void printFromDB() throws SQLException {

        Connection conn = null;
        try {
            String sql = "SELECT * FROM patientsDB;";

            DbHelper dal = new DbHelper();

            conn = dal.getConnection();

            ResultSet rs = dal.executeQuery(conn, sql);

            while (rs.next()) {
                print(rs.getString("username"));
                print(rs.getString("firstname"));
                print(rs.getString("lastname"));
                print(rs.getString("DOB"));
                print(rs.getString("Phone"));
                print(rs.getString("email"));
                print(rs.getString("userpassword"));

            }

            conn.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

    }

    private static boolean doesUserNameExist(String username) throws SQLException {

        Connection conn = null;

        try {
            String sql = "SELECT * FROM patientsDB WHERE username='" + username + "'";

            DbHelper dbHelper = new DbHelper();

            conn = dbHelper.getConnection();

            ResultSet rs = dbHelper.executeQuery(conn, sql);

            if (rs != null) {
                return true;
            }

            conn.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
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
            String sql = "INSERT INTO patientsDB (username, firstname, lastname, DOB, email, userpassword, phone)" +
                    "VALUES (' " + userName + " ', ' "
                    + firstName
                    + " ', ' " + lastName
                    + " ', ' " + dob
                    + " ', ' " + email
                    + " ', ' " + password
                    + " ', ' " + phone + " ');";

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

    private static boolean doesEmailExist(TextField email) {

        return true;

    }

    public void print(String str) {
        System.out.println(str);
    }

}
