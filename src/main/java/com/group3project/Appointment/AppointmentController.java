package com.group3project.Appointment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.time.LocalDate;

import com.group3project.Utils.MainFx;

public class AppointmentController {
    @FXML
    private Button btnBookAppointment;

    @FXML
    private ComboBox<String> comboBoxSelection;

    @FXML
    private DatePicker datePicker;

    @FXML
    void handleComboBox(ActionEvent event) {

    }

    @FXML
    void handleDatePicker(ActionEvent event) {

    }

    @FXML
    void handleBookAppointment(ActionEvent event) {
        // LocalDate value = datePicker.getValue();
        // this.appointmentDateIndex = value.getDayOfMonth();
        // String doctorName = comboBoxSelection.getValue().toString();
        // System.out.println(datePicker.getValue());
        // System.out.println(doctorName);
        openNewScene(event, this.homepageScene, "Homepage", false);
    }

    // @FXML
    // public void initialize() {
    // comboBoxSelection.getItems().removeAll(comboBoxSelection.getItems());
    // comboBoxSelection.getItems().addAll(
    // "General Practitioner",
    // "Gynecologist",
    // "Dermatologist",
    // "ENT",
    // "Pulmonologist");
    // }

    private Scene homepageScene;

    public void setHomepageScene(Scene scene) {
        homepageScene = scene;
    }

    public void openNewScene(ActionEvent actionEvent, Scene scene, String title, boolean resizable) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        MainFx.updateUI(primaryStage, title, resizable);
    }
}
