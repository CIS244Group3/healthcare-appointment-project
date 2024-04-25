package com.group3project.Appointment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import com.group3project.Patient_Doctor.Doctor;
import com.group3project.Patient_Doctor.Patient;
import com.group3project.Utils.DbHelper;
import com.group3project.Utils.MainFx;

public class AppointmentController {
    @FXML
    private Button btnBackHome;

    @FXML
    private Button btnBookAppointment;

    @FXML
    private ComboBox<Doctor> comboBoxSelection;

    @FXML
    private ComboBox<LocalTime> comboBoxTimeSelection;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private DatePicker datePicker;

    private Patient currentUser;
    private Doctor currentDoctor;
    private ObservableList<LocalTime> availabilityList;
    private LocalTime currentTime;
    private LocalDate currentDate;

    public void setCurrentUser(Patient user) {
        this.currentUser = user;
    }

    @FXML
    void handleComboBox(ActionEvent event) {
        setCurrentDoctor(comboBoxSelection.getValue());
        if (this.currentDoctor != null && this.currentUser != null && this.currentDate != null) {
            updateOptions();
        }

    }

    @FXML
    void handleBackHome(ActionEvent event) {
        openNewScene(event, this.homepageScene, "Welcome " + this.currentUser.getName() + "!", false);

    }

    @FXML
    void handleDatePicker(ActionEvent event) {
        LocalDate currentDate = datePicker.getValue();
        setCurrentDate(currentDate);

        if (this.currentDoctor != null && this.currentUser != null && this.currentDate != null) {
            updateOptions();
        }

    }

    @FXML
    void handleTimeSelection(ActionEvent event) {
        setCurrentTime(comboBoxTimeSelection.getValue());
    }

    @FXML
    void handleBookAppointment(ActionEvent event) {

        try {
            String sql = "INSERT INTO appointment (patientid, doctorid, appointmentdate, appointmenttime) VALUES (?, ?, ?, ?)";

            DbHelper dal = new DbHelper();

            Connection conn = dal.getConnection();

            PreparedStatement pStmt = conn.prepareStatement(sql);

            pStmt.setInt(1, this.currentUser.getId());
            pStmt.setInt(2, this.currentDoctor.getId());
            pStmt.setDate(3, Date.valueOf(this.currentDate));
            pStmt.setTime(4, Time.valueOf(this.currentTime));

            int inserted = pStmt.executeUpdate();

            if (inserted == 1) {
                System.out.println("Record Inserted");

            } else {
                System.out.println("No record found");
            }

            conn.close();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        openNewScene(event, this.homepageScene, "Welcome " + this.currentUser.getName() + "!", false);
    }

    private Scene homepageScene;

    public void setHomepageScene(Scene scene) {
        homepageScene = scene;
    }

    public void setCurrentDoctor(Doctor doctor) {
        this.currentDoctor = doctor;
    }

    public void setCurrentTime(LocalTime time) {
        this.currentTime = time;
    }

    public void setCurrentDate(LocalDate date) {
        this.currentDate = date;
    }

    private void updateOptions() {
        try {
            String sql = "SELECT appointmenttime FROM appointment WHERE doctorid = ? AND appointmentdate = ?";

            DbHelper dal = new DbHelper();

            Connection conn = dal.getConnection();

            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, this.currentDoctor.getId());
            pStmt.setDate(2, Date.valueOf(this.currentDate));

            ResultSet rs = pStmt.executeQuery();

            this.availabilityList = FXCollections.observableArrayList();

            while (rs.next()) {
                LocalTime time = rs.getTime("appointmenttime").toLocalTime();
                this.currentDoctor.makeTimeUnavailable(time);
                System.out.println("SHOULD BE PRINTED 5 TIMES");
            }
            for (Map.Entry<LocalTime, Boolean> entry : this.currentDoctor.getAvailability().entrySet()) {
                LocalTime key = entry.getKey();
                boolean value = entry.getValue();
                if (value) {
                    this.availabilityList.add(key);
                }

            }
            comboBoxTimeSelection.setItems(this.availabilityList);

            conn.close();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        // datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
        // if (newValue != null) {

        // comboBoxTimeSelection.setItems(this.availabilityList);
        // }
        // });
    }

    public void openNewScene(ActionEvent actionEvent, Scene scene, String title, boolean resizable) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        MainFx.updateUI(primaryStage, title, resizable);
    }

    private void populateDoctors() {

        try {
            String sql = "SELECT * FROM doctor";

            DbHelper dal = new DbHelper();

            Connection conn = dal.getConnection();

            PreparedStatement pStmt = conn.prepareStatement(sql);

            ResultSet rs = pStmt.executeQuery();

            ObservableList<Doctor> doctors = FXCollections.observableArrayList();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = "Dr. " + rs.getString("firstname") + " " + rs.getString("lastname");
                String specialty = rs.getString("specialty");
                doctors.add(new Doctor(id, name, specialty));
            }
            comboBoxSelection.setItems(doctors);

            conn.close();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    @FXML
    private void initialize() {
        populateDoctors();
        this.datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 || date.isAfter(today.plusDays(180))
                        || date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY);
            }
        });
    }
}
