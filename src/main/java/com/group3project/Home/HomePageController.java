package com.group3project.Home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import com.group3project.Patient_Doctor.Patient;
import com.group3project.Utils.MainFx;

public class HomePageController {

    public ImageView appLogo;

    private void loadScene(String fxmlFile, ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void OnHomePageButtonClick(ActionEvent event) throws IOException {
        openNewScene(event, this.homepageScene, "Homepage", false);

    }

    public void onContactUsButtonClick(ActionEvent event) throws IOException {
        openNewScene(event, contactUScene, "Contact Us", false);

    }

    public void onAppointmentButtonClick(ActionEvent event) throws IOException {
        openNewScene(event, this.appointmentScene, "Make an Appointment", false);

    }

    public void onProfileButtonClick(ActionEvent event) throws IOException {
        loadScene("Profile.fxml", event);
    }

    public void onHomeButtonClick(ActionEvent event) throws IOException {
        // openNewScene(event, this.homepageScene, "Homepage", false);
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Patient patient = (Patient) stage.getUserData();
        System.out.println(patient.getName());
    }

    public void onPastAppointmentButtonClick(ActionEvent event) throws IOException {
        openNewScene(event, this.pastAppointmentScene, "Past Appointments", false);

    }

    @FXML
    void onLogOutClick(ActionEvent event) {
        logOutScene(event);
    }

    @FXML
    private Button btnAppointment;

    @FXML
    private Button btnContactUs;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnPastAppointment;

    @FXML
    private Button btnProfile;

    @FXML
    private Button btnHomePage;

    @FXML
    private Label testing;

    @FXML
    private Button logout;

    private Scene loginScene;

    public void setLoginScene(Scene scene) {
        loginScene = scene;
    }

    private Scene contactUScene;

    public void setContactUsScene(Scene scene) {
        contactUScene = scene;
    }

    private Scene homepageScene;

    public void setHomepageScene(Scene scene) {
        homepageScene = scene;
    }

    private Scene pastAppointmentScene;

    public void setPastAppointsScene(Scene scene) {
        pastAppointmentScene = scene;
    }

    private Scene appointmentScene;

    public void setAppointmentScene(Scene scene) {
        appointmentScene = scene;
    }

    public void openNewScene(ActionEvent actionEvent, Scene scene, String title, boolean resizable) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        MainFx.updateUI(primaryStage, title, resizable);
    }

    void logOutScene(ActionEvent event) {
        openNewScene(event, this.loginScene, "Login Is Required", false);

    }

}
