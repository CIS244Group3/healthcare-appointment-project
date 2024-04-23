package com.group3project.Home;

import java.io.IOException;

import com.group3project.Patient_Doctor.Patient;
import com.group3project.Utils.MainFx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.Node;

import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ContactUsController {

    @FXML
    private Button btnHomePage;

    @FXML
    public void OnHomePageButtonClick(ActionEvent event) throws IOException {
        openNewScene(event, this.homepageScene, "Welcome " + this.currentUser.getName() + "!", false);

    }

    private Scene homepageScene;

    public void setHomepageScene(Scene scene) {
        homepageScene = scene;
    }

    public void openNewScene(ActionEvent actionEvent, Scene scene, String title, boolean resizable) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        MainFx.updateUI(primaryStage, title, resizable);
    }

    private Patient currentUser;

    public void setCurrentUser(Patient user) {
        this.currentUser = user;
    }

}