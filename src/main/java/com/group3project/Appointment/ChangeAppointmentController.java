package com.group3project.Appointment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import com.group3project.Patient_Doctor.Doctor;
import com.group3project.Patient_Doctor.Patient;
import com.group3project.Utils.DbHelper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;

public class ChangeAppointmentController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ComboBox<LocalTime> comboBoxTimeSelection;

    @FXML
    private DatePicker datePicker;

    private LocalTime currentTime;
    private LocalDate currentDate;
    private Patient currentUser;
    private ObservableList<LocalTime> availabilityList;

    @FXML
    void handleDatePicker(ActionEvent event) {
        setCurrentDate(datePicker.getValue());
        if (this.currentDoctorId != 0 && this.currentUser != null && datePicker.getValue() != null) {

            // updateOptions();
        }
    }

    @FXML
    void handleTimeSelection(ActionEvent event) {

    }

    private int currentDoctorId;

    public void setCurrentTime(LocalTime time) {
        this.currentTime = time;
    }

    public void setCurrentDate(LocalDate date) {
        this.currentDate = date;
    }

    public void setCurrentUser(Patient user) {
        this.currentUser = user;
    }

    public void setCurrentDoctorId(int doctorId) {
        this.currentDoctorId = doctorId;
    }

    // private void updateOptions() {
    // try {
    // String sql = "SELECT appointmenttime FROM appointment WHERE doctorid = ? AND
    // appointmentdate = ?";

    // DbHelper dal = new DbHelper();

    // Connection conn = dal.getConnection();

    // PreparedStatement pStmt = conn.prepareStatement(sql);
    // pStmt.setInt(1, this.currentDoctorId);
    // pStmt.setDate(2, Date.valueOf(this.currentDate));

    // ResultSet rs = pStmt.executeQuery();

    // this.availabilityList = FXCollections.observableArrayList();

    // while (rs.next()) {
    // LocalTime time = rs.getTime("appointmenttime").toLocalTime();
    // this.currentDoctor.makeTimeUnavailable(time);
    // }
    // for (Map.Entry<LocalTime, Boolean> entry :
    // this.currentDoctor.getAvailability().entrySet()) {
    // LocalTime key = entry.getKey();
    // boolean value = entry.getValue();
    // if (value) {
    // this.availabilityList.add(key);
    // }

    // }
    // comboBoxTimeSelection.setItems(this.availabilityList.sorted());

    // conn.close();
    // } catch (Exception ex) {
    // System.out.println("Error: " + ex.getMessage());
    // }

    // }

    @FXML
    private void initialize() {
        this.datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 || date.isAfter(today.plusDays(180))
                        || date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY);
            }
        });
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && newValue != null && !newValue.isEqual(oldValue) && this.currentDoctorId != 0) {
                System.out.println("Here I am");

                // this.currentDoctor.setNewAvailability();
            }
        });
    }

}