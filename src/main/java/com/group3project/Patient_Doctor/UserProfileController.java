package com.group3project.Patient_Doctor;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.beans.property.BooleanProperty;

import com.group3project.Utils.DbHelper;
import com.group3project.Utils.MainFx;

public class UserProfileController {

    @FXML
    private Label clinicNameLabel;

    @FXML
    private Label doctorNameLabel;

    @FXML
    private VBox editBox;

    @FXML
    private Button editButton;

    @FXML
    private TextField emailInput;

    @FXML
    private Label emailLabel;

    @FXML
    private Button homeButton;

    @FXML
    private TextField hospitalInput;

    @FXML
    private TextField insuranceInput;

    @FXML
    private Label insuranceLabel;

    @FXML
    private VBox mainContainer;

    @FXML
    private ComboBox<String> pcpInput;

    @FXML
    private TextField pharmacyInput;

    @FXML
    private Label pharmacyLabel;

    @FXML
    private Button saveButton;

    @FXML
    private Button clearButton;

    @FXML
    private TextField usernameInput;

    @FXML
    private Label usernameLabel;

    @FXML
    void handleBackHome(ActionEvent event) {
        clearForm();
        openNewScene(event, homepageScene, "Homepage", false);
    }

    @FXML
    void handleEdit(ActionEvent event) {
        showEditSetion();
    }

    @FXML
    void handleSave(ActionEvent event) {
        if (hasProfile) {

            updateUserProfile();
        } else {
            insertUserProfile();
        }

    }

    @FXML
    void handleClear(ActionEvent event) {
        clearForm();

    }

    private Scene homepageScene;

    private Patient currentUser;
    private boolean hasProfile;
    private BooleanProperty editSection = new SimpleBooleanProperty(false);

    public void showEditSetion() {
        this.editSection.set(true);
    }

    public void setCurrentUser(Patient user) {
        this.currentUser = user;
    }

    public void setHomepageScene(Scene scene) {
        homepageScene = scene;
    }

    public void setUserInfo() {
        this.usernameLabel.setText(this.currentUser.getUsername());
        this.emailLabel.setText(this.currentUser.getEmail());

    }

    public void populateUserInfo() {
        String query = "SELECT * FROM usersProfile WHERE patientid = ?";
        try (

                Connection conn = new DbHelper().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, this.currentUser.getId());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                this.hasProfile = true;
                doctorNameLabel.setText(rs.getString("doctorname"));
                clinicNameLabel.setText(rs.getString("clinicname"));
                insuranceLabel.setText(rs.getString("insurance"));
                pharmacyLabel.setText(rs.getString("pharmacy"));

            }
        } catch (SQLException e) {
            showAlertDialog(Alert.AlertType.ERROR, "Database Error", "Error loading user data: " + e.getMessage());
        }
    }

    private void updateUserProfile() {
        String update = "UPDATE usersProfile SET doctorname = ?, clinicname = ?, insurance = ?, pharmacy = ? WHERE patientid = ?";
        try (Connection conn = new DbHelper().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(update)) {
            // Updating fields except for password
            pstmt.setString(1, pcpInput.getValue());
            pstmt.setString(2, hospitalInput.getText());
            pstmt.setString(3, insuranceInput.getText());
            pstmt.setString(4, pharmacyInput.getText());
            pstmt.setInt(5, this.currentUser.getId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                showAlertDialog(Alert.AlertType.INFORMATION, "Update Successful", "User profile updated successfully.");
                this.doctorNameLabel.setText(pcpInput.getValue());
                this.clinicNameLabel.setText(hospitalInput.getText());
                this.insuranceLabel.setText(insuranceInput.getText());
                this.pharmacyLabel.setText(pharmacyInput.getText());
                clearForm();
                this.editSection.set(false);
            } else {
                showAlertDialog(Alert.AlertType.ERROR, "Update Failed", "No changes made.");
            }
        } catch (SQLException e) {
            showAlertDialog(Alert.AlertType.ERROR, "Database Error", "Error updating user profile: " + e.getMessage());
        }
    }

    private void insertUserProfile() {
        String update = "INSERT INTO usersProfile (patientid, doctorname, clinicname, insurance, pharmacy) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = new DbHelper().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(update)) {
            // Updating fields except for password
            pstmt.setInt(1, this.currentUser.getId());
            pstmt.setString(2, pcpInput.getValue());
            pstmt.setString(3, hospitalInput.getText());
            pstmt.setString(4, insuranceInput.getText());
            pstmt.setString(5, pharmacyInput.getText());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                showAlertDialog(Alert.AlertType.INFORMATION, "Update Successful", "User profile updated successfully.");
                this.doctorNameLabel.setText(pcpInput.getValue());
                this.clinicNameLabel.setText(hospitalInput.getText());
                this.insuranceLabel.setText(insuranceInput.getText());
                this.pharmacyLabel.setText(pharmacyInput.getText());
                clearForm();
                this.editSection.set(false);
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

    private void populateDoctors() {

        try {
            String sql = "SELECT firstname, lastname FROM doctor WHERE specialty = ? OR specialty = ?";

            DbHelper dal = new DbHelper();

            Connection conn = dal.getConnection();

            PreparedStatement pStmt = conn.prepareStatement(sql);

            pStmt.setString(1, "Family medicine");
            pStmt.setString(2, "Internal medicine");

            ResultSet rs = pStmt.executeQuery();

            ObservableList<String> doctors = FXCollections.observableArrayList();

            while (rs.next()) {
                String name = "Dr. " + rs.getString("firstname") + " " + rs.getString("lastname");
                doctors.add(name);
            }
            pcpInput.setItems(doctors);

            conn.close();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void openNewScene(ActionEvent actionEvent, Scene scene, String title, boolean resizable) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        MainFx.updateUI(primaryStage, title, resizable);
    }

    private void clearForm() {
        // Clears all fields but leaves password field empty as it's not loaded
        usernameInput.clear();
        emailInput.clear();
        pcpInput.getSelectionModel().clearSelection();
        hospitalInput.clear();
        insuranceInput.clear();
        pharmacyInput.clear();
    }

    @FXML
    private void initialize() {
        editBox.visibleProperty().bind(editSection);
        populateDoctors();
    }
}
