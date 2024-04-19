package com.group3project.Registration;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.group3project.Patient_Doctor.Patient;
import com.group3project.Utils.*;

public class LoginController {

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField pwfpassword;

    @FXML
    private TextField txfusername;

    @FXML
    private Hyperlink register;
    private Patient patient;

    @FXML
    public void initialize() {
        txfusername.setText("");
        pwfpassword.setText("");
    }

    private Scene registrationScene;

    private Scene homepageScene;

    public void setRegistrationScene(Scene scene) {
        registrationScene = scene;
    }

    public void setHomepageScene(Scene scene) {
        homepageScene = scene;
    }

    public void openNewScene(ActionEvent actionEvent, Scene scene, String title, boolean resizable) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        MainFx.updateUI(primaryStage, title, resizable);
    }

    private void clearField() {
        this.txfusername.setText("");
        this.pwfpassword.setText("");
    }

    @FXML
    void switchToRegistrationPage(ActionEvent event) {
        openNewScene(event, this.registrationScene, "Registration", false);
        clearField();

    }

    void switchToHomePage(ActionEvent event, Patient patient) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setUserData(patient);
        openNewScene(event, this.homepageScene, "Homepage", false);
        clearField();

    }

    private boolean isValidLogin() {
        if (this.txfusername.getText().equals("") || this.pwfpassword.getText().equals("")) {
            return false;
        } else {
            String username = this.txfusername.getText();
            String password = this.pwfpassword.getText();
            try {
                // String password = ProjUtil.getSHA("1234");
                String sql = "SELECT * FROM patients WHERE password = ? AND username = ? ";

                DbHelper dal = new DbHelper();

                Connection conn = dal.getConnection();

                PreparedStatement pStmt = conn.prepareStatement(sql);
                //
                // // Set Parameters
                pStmt.setString(1, password);
                pStmt.setString(2, username);

                ResultSet rs = pStmt.executeQuery();

                if (rs.next()) {
                    System.out.println("Found record");

                    System.out.println("email: " + rs.getString("email"));
                    System.out.println("password: " + rs.getString("password"));
                    System.out.println("phone number: " + rs.getString("phonenumber"));
                    String usernameString = rs.getString("username");
                    String nameString = rs.getString("firstname") + " " + rs.getString("lastname");
                    String emailString = rs.getString("email");
                    this.patient = new Patient(nameString, usernameString, emailString);
                } else {
                    System.out.println("No record found");
                    conn.close();
                    return false;
                }

            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }

        return true;
    }

    @FXML
    void onLogin(ActionEvent event) {
        if (isValidLogin()) {
            switchToHomePage(event, this.patient);
        } else {
            showMessage("Invalid Credentials");
            clearField();

        }
    }

    private void showMessage(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        Application.launch();
    }

}
