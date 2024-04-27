package com.group3project.Appointment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;

import com.group3project.Patient_Doctor.Patient;
import com.group3project.Utils.DbHelper;
import com.group3project.Utils.MainFx;

public class PastAppointmentsController {

    @FXML
    private Button OnBackToHomePage;

    @FXML
    private TableColumn<PastAppointment, LocalDate> dateColumn;

    @FXML
    private TableColumn<PastAppointment, String> doctorColumn;

    @FXML
    private TableColumn<PastAppointment, String> specialtyColumn;

    @FXML
    private TableView<PastAppointment> tableView;

    @FXML
    private TableColumn<PastAppointment, LocalTime> timeColumn;

    private Patient currentUser;

    public void setCurrentUser(Patient user) {
        this.currentUser = user;
    }

    public Patient getCurrentUser() {
        return this.currentUser;
    }

    @FXML
    void display(ActionEvent event) {
        openNewScene(event, this.homepageScene, "Welcome " + this.currentUser.getName() + "!", false);

    }

    // private ObservableList<PastAppointment> pastAppointments;

    private Scene homepageScene;

    public void setHomepageScene(Scene scene) {
        homepageScene = scene;
    }

    public void openNewScene(ActionEvent actionEvent, Scene scene, String title, boolean resizable) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        MainFx.updateUI(primaryStage, title, resizable);
    }

    private ObservableList<PastAppointment> getAppointments() {
        ObservableList<PastAppointment> listPastAppointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT appointment.appointmentdate, appointment.appointmenttime, doctor.firstname, doctor.lastname, doctor.specialty FROM appointment JOIN doctor ON appointment.doctorid=doctor.id WHERE patientid=?";

            DbHelper dal = new DbHelper();

            Connection conn = dal.getConnection();

            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, this.currentUser.getId());

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                LocalDate date = rs.getDate("appointmentdate").toLocalDate();
                LocalTime time = rs.getTime("appointmenttime").toLocalTime();
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String specialty = rs.getString("specialty");
                if (date.isBefore(LocalDate.now())) {
                    listPastAppointments.add(new PastAppointment(date, time, firstName, lastName, specialty));
                }
            }

            conn.close();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return listPastAppointments;

    }

    public void setUpTableView() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<PastAppointment, LocalDate>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<PastAppointment, LocalTime>("time"));
        doctorColumn.setCellValueFactory(new PropertyValueFactory<PastAppointment, String>("doctor"));
        specialtyColumn.setCellValueFactory(new PropertyValueFactory<PastAppointment, String>("specialty"));

        tableView.setItems(getAppointments());
    }

}
