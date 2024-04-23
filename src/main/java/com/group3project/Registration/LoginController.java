package com.group3project.Registration;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import com.group3project.Home.HomePageController;
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

    private RegistrationController currentRegistrationController;

    public void setRegistrationScene(Scene scene) {
        this.registrationScene = scene;
    }

    public void setCurrentRegistrationController(RegistrationController controller) {
        this.currentRegistrationController = controller;
    }

    public void setHomepageScene(Scene scene) {
        this.homepageScene = scene;
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
    void switchToRegistrationPage(ActionEvent event) throws Exception {

        if (this.registrationScene == null) {
            FXMLLoader fxmlLoaderRegistration = new FXMLLoader(
                    getClass().getResource("../../../fxml/RegistrationUI.fxml"));
            Parent registrationRoot = fxmlLoaderRegistration.load();
            RegistrationController registrationCont = (RegistrationController) fxmlLoaderRegistration.getController();
            Scene regiScene = new Scene(registrationRoot);

            setCurrentRegistrationController(registrationCont);
            setRegistrationScene(regiScene);
            currentRegistrationController.setLoginScene(((Node) event.getSource()).getScene());
        }
        currentRegistrationController.setUserName(this.txfusername.getText());
        openNewScene(event, this.registrationScene, "Registration", false);
        clearField();

    }

    void switchToHomePage(ActionEvent event, Patient patient) throws Exception {
        FXMLLoader fxmlLoaderHomepage = new FXMLLoader(getClass().getResource("../../../fxml/Homepage.fxml"));
        Parent homepageRoot = fxmlLoaderHomepage.load();
        HomePageController homepageCont = (HomePageController) fxmlLoaderHomepage.getController();
        this.homepageScene = new Scene(homepageRoot);
        homepageCont.setCurrentUser(patient);

        openNewScene(event, this.homepageScene, "Welcome " + this.patient.getName() + "!", false);
        clearField();

    }

    private boolean isValidLogin() {
        if (this.txfusername.getText().equals("") || this.pwfpassword.getText().equals("")) {
            return false;
        } else {
            String username = this.txfusername.getText();
            String password = this.pwfpassword.getText();
            String hashedPassword = ProjUtil.getSHA(password);

            try {
                String sql = "SELECT * FROM patients WHERE password = ? AND username = ? ";

                DbHelper dal = new DbHelper();

                Connection conn = dal.getConnection();

                PreparedStatement pStmt = conn.prepareStatement(sql);
                //
                // // Set Parameters
                pStmt.setString(1, hashedPassword);
                pStmt.setString(2, username);

                ResultSet rs = pStmt.executeQuery();

                if (rs.next()) {
                    String usernameString = rs.getString("username");
                    String nameString = rs.getString("firstname") + " " + rs.getString("lastname");
                    String emailString = rs.getString("email");
                    int id = rs.getInt("id");
                    LocalDate dob = rs.getDate("dob").toLocalDate();
                    String phoneNumber = rs.getString("phonenumber");
                    String gender = rs.getString("gender");
                    this.patient = new Patient(id, nameString, dob, usernameString, emailString, phoneNumber, gender);
                } else {
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
    void onLogin(ActionEvent event) throws Exception {
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

}
