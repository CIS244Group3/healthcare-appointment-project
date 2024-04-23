package com.group3project.Patient_Doctor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.group3project.Utils.DbHelper;
import com.group3project.Utils.MainFx;

public class UserProfileController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField doctorNameField;
    @FXML
    private TextField clinicNameField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private Scene homepageScene;

    private Patient currentUser;

    public void setCurrentUser(Patient user) {
        this.currentUser = user;
    }

    public void setHomepageScene(Scene scene) {
        homepageScene = scene;
    }

    @FXML
    private void initialize() {
        // Load user data of the logged-in user
        String loggedInUsername = UserSession.getLoggedInUsername();
        if (loggedInUsername != null && !loggedInUsername.isEmpty()) {
            loadUserData(loggedInUsername);
        }
    }

    private void loadUserData(String username) {
        String query = "SELECT username, email, doctorName, clinicName FROM users WHERE username = ?";
        try (Connection conn = new DbHelper().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                usernameField.setText(rs.getString("username"));
                // passwordField is intentionally left out for security reasons
                emailField.setText(rs.getString("email"));
                doctorNameField.setText(rs.getString("doctorName"));
                clinicNameField.setText(rs.getString("clinicName"));
            }
        } catch (SQLException e) {
            showAlertDialog(Alert.AlertType.ERROR, "Database Error", "Error loading user data: " + e.getMessage());
        }
    }

    @FXML
    private void handleSaveAction() {
        updateUserProfile();
    }

    private void updateUserProfile() {
        String update = "UPDATE users SET email = ?, doctorName = ?, clinicName = ? WHERE username = ?";
        try (Connection conn = new DbHelper().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(update)) {
            // Updating fields except for password
            pstmt.setString(1, emailField.getText());
            pstmt.setString(2, doctorNameField.getText());
            pstmt.setString(3, clinicNameField.getText());
            pstmt.setString(4, usernameField.getText());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                showAlertDialog(Alert.AlertType.INFORMATION, "Update Successful", "User profile updated successfully.");
            } else {
                showAlertDialog(Alert.AlertType.ERROR, "Update Failed", "No changes made.");
            }
        } catch (SQLException e) {
            showAlertDialog(Alert.AlertType.ERROR, "Database Error", "Error updating user profile: " + e.getMessage());
        }
    }

    private void showAlertDialog(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        clearForm();
        openNewScene(event, homepageScene, "Homepage", false);
    }

    public void openNewScene(ActionEvent actionEvent, Scene scene, String title, boolean resizable) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        MainFx.updateUI(primaryStage, title, resizable);
    }

    private void clearForm() {
        // Clears all fields but leaves password field empty as it's not loaded
        usernameField.clear();
        passwordField.clear();
        emailField.clear();
        doctorNameField.clear();
        clinicNameField.clear();
    }
}
