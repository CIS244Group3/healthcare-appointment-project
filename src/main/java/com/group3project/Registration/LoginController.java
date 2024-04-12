package com.group3project.Registration;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import com.group3project.Utils.*;

public class LoginController extends Application {

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField pwfpassword;

    @FXML
    private TextField txfusername;

    @FXML
    private Hyperlink register;

    private EventHandler<ActionEvent> ehae;

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

    private boolean isValidregistration() {
        String username = this.txfusername.getText();
        String password = this.pwfpassword.getText();
        if (this.txfusername.getText().equals("") || this.pwfpassword.getText().equals("")) {
            showMessage("Please type in Username");
            showMessage("Please type in Password");
            return false;
        } else {
            return true;
        }
    }

    @FXML
    void switchToRegistrationPage(ActionEvent event) {
        openNewScene(event, this.registrationScene, "Registration", false);

    }

    void switchToHomePage(ActionEvent event) {
        openNewScene(event, this.homepageScene, "Homepage", false);

    }

    @FXML
    void onLogin(ActionEvent event) {
        // if (isValidLogin()) {
        // } else {
        // showMessage("Invalid");
        // }
        switchToHomePage(event);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

    public void setOnAction(EventHandler<ActionEvent> ehae) {
        this.ehae = ehae;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Application/Login.fxml"));
        stage.setScene(new Scene(root));
        stage.show();

    }
}
