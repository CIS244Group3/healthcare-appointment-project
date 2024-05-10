package com.group3project.Appointment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import com.group3project.Home.HomePageController;
import com.group3project.Patient_Doctor.Doctor;
import com.group3project.Patient_Doctor.Patient;
import com.group3project.Utils.DbHelper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ChangeAppointmentController {
    private Doctor placeholder;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ComboBox<LocalTime> comboBoxTimeSelection;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button btnBackHome;

    @FXML
    private Button btnChangeAppointment;

    private LocalTime newTime;
    private LocalDate newDate;
    private Patient currentUser;
    private ObservableList<LocalTime> availabilityList;

    @FXML
    void handleBackHome(ActionEvent event) {
        setCurrentDoctorId(0);
        setCurrentUser(null);
        setHomepageController(null);
        setNewDate(null);
        setNewTime(null);
        datePicker.getEditor().clear();
        comboBoxTimeSelection.getSelectionModel().clearSelection();
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
    }

    @FXML
    void handleChangeAppointment(ActionEvent event) {
        if (this.newDate != null && this.newTime != null) {
            updateAppointment();
        }
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
    }

    @FXML
    void handleDatePicker(ActionEvent event) {
        setNewDate(datePicker.getValue());
        if (this.currentDoctorId != 0 && this.currentUser != null && datePicker.getValue() != null) {

            updateOptions();
        }
    }

    @FXML
    void handleTimeSelection(ActionEvent event) {
        setNewTime(comboBoxTimeSelection.getValue());

    }

    private int currentDoctorId;
    private PastAppointment selectedAppointment;
    private HomePageController homePageController;

    public void setNewTime(LocalTime time) {
        this.newTime = time;
    }

    public void setNewDate(LocalDate date) {
        this.newDate = date;
    }

    public void setCurrentUser(Patient user) {
        this.currentUser = user;
    }

    public void setCurrentDoctorId(int doctorId) {
        this.currentDoctorId = doctorId;
    }

    public void setSelectedAppointment(PastAppointment appointment) {
        this.selectedAppointment = appointment;
    }

    public void setHomepageController(HomePageController controller) {
        this.homePageController = controller;
    }

    private void updateAppointment() {
        String update = "UPDATE appointment SET appointmentdate = ?, appointmenttime= ? WHERE id=?";
        try (Connection conn = new DbHelper().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(update)) {
            pstmt.setDate(1, Date.valueOf(this.newDate));
            pstmt.setTime(2, Time.valueOf(this.newTime));
            pstmt.setInt(3, this.selectedAppointment.getId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                showAlertDialog(Alert.AlertType.INFORMATION, "Update Successful",
                        "Appointment change has been made");
                homePageController.hideEditSetion();
                homePageController.getTableView().getItems().remove(this.selectedAppointment);
                this.selectedAppointment.setDate(this.newDate);
                this.selectedAppointment.setTime(this.newTime);
                homePageController.getTableView().getItems()
                        .add(this.selectedAppointment);

                this.selectedAppointment = null;
            } else {
                showAlertDialog(Alert.AlertType.ERROR, "Update Failed", "Please try again");
            }
        } catch (SQLException e) {
            showAlertDialog(Alert.AlertType.ERROR, "Database Error", "Error updating user profile: " + e.getMessage());
        }
    }

    private void updateOptions() {
        this.placeholder = new Doctor(0, null, null, null, null);
        try {
            String sql = "SELECT appointmenttime FROM appointment WHERE doctorid = ? AND appointmentdate = ?";

            DbHelper dal = new DbHelper();

            Connection conn = dal.getConnection();

            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, this.currentDoctorId);
            pStmt.setDate(2, Date.valueOf(this.newDate));

            ResultSet rs = pStmt.executeQuery();

            this.availabilityList = FXCollections.observableArrayList();

            while (rs.next()) {
                LocalTime time = rs.getTime("appointmenttime").toLocalTime();
                this.placeholder.makeTimeUnavailable(time);
            }
            for (Map.Entry<LocalTime, Boolean> entry : this.placeholder.getAvailability().entrySet()) {
                LocalTime key = entry.getKey();
                boolean value = entry.getValue();
                if (value) {
                    this.availabilityList.add(key);
                }

            }
            comboBoxTimeSelection.setItems(this.availabilityList.sorted());

            conn.close();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }

    private void showAlertDialog(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

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