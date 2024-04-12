package com.group3project.Appointment;

import java.time.LocalDate;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.*;

public class Appointment extends Application {

    private String doctorName;
    private int appointmentDateIndex;

    @Override
    public void start(Stage stage) {

        FXMLLoader testUI = new FXMLLoader(getClass().getResource("test.fxml"));
        System.out.println("Here!");
        Parent testUIRoot = testUI.load();
        System.out.println("Past Here!");

        TestController testCont = (TestController) testUI.getController();

        Scene luiScene = new Scene(testUIRoot);
        this.showUI(luiScene, StageStyle.DECORATED, "Login Is Required", false);

        doctorComboBox.getItems().addAll(
                "General Practitioner",
                "Gynecologist",
                "Dermatologist",
                "ENT",
                "Pulmonologist");
        doctorComboBox.getSelectionModel().selectFirst();

        Button button = new Button("Schedule Appointment");

        button.setOnAction(action -> {
            LocalDate value = startDatePicker.getValue();
            this.appointmentDateIndex = value.getDayOfMonth();
            this.doctorName = doctorComboBox.getValue().toString();
            System.out.println(this.appointmentDateIndex);
            System.out.println(this.doctorName);

        });
    }

    public void showUI(Scene scene, StageStyle stageStyle, String title, boolean resizable) {
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(title);

        stage.initStyle(stageStyle);

        stage.setResizable(resizable);

        stage.show();
    }
}
