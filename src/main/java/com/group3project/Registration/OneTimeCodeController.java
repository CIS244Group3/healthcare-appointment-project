package com.group3project.Registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.group3project.Utils.DbHelper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OneTimeCodeController {

    @FXML
    private TextField codeTextField;

    @FXML
    private Button submitCodeButton;

    private long oneTimeCode;

    public void setOneTimeCode(long oneTimeCode) {
        this.oneTimeCode = oneTimeCode;
    }

    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    @FXML
    void handleSubmit(ActionEvent event) {
        if (verifyUser()) {
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.close();

        }
    }

    private boolean verifyUser() {
        String update = "UPDATE patients SET onetimecode = ?, verified = ? WHERE onetimecode = ? AND username = ?";
        try (Connection conn = new DbHelper().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(update)) {
            // Updating fields except for password
            pstmt.setLong(1, 0);
            pstmt.setBoolean(2, true);
            pstmt.setLong(3, this.oneTimeCode);
            pstmt.setString(4, this.username);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                showMessage("Verification Successful: Please Log In.");
                return true;

            } else {
                showMessage("Wrong activation code");
            }
        } catch (SQLException e) {
            e.getMessage();
            showMessage("Database Error: Error verifying account: ");
        }
        return false;
    }

    private void showMessage(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
