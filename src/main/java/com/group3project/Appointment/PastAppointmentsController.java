package com.group3project.Appointment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import com.group3project.Patient_Doctor.Patient;
import com.group3project.Utils.MainFx;

public class PastAppointmentsController implements Initializable {

    @FXML
    private Button OnBackToHomePage;

    @FXML
    private Label lab;

    @FXML
    void display(ActionEvent event) {
        lab.setText("Back To Home Page ");
        openNewScene(event, this.homepageScene, "Homepage", false);

    }

    @FXML
    private TableColumn<Person, Integer> appointmentsColumn;

    @FXML
    private TableColumn<Person, LocalDate> dateColumn;

    @FXML
    private AnchorPane Home;

    @FXML
    private TableColumn<Person, LocalTime> timeColumn;

    @FXML
    private TableView<Person> tableView;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    private Patient currentUser;

    public void setCurrentUser(Patient user) {
        this.currentUser = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentsColumn.setCellValueFactory(new PropertyValueFactory<Person, Integer>("appointments"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Person, LocalTime>("time"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Person, LocalDate>("date"));

        tableView.setItems(getPeople());

    }

    public ObservableList<Person> getPeople() {
        ObservableList<Person> people = FXCollections.observableArrayList();
        people.add(new Person(1, LocalTime.of(12, 30), LocalDate.of(2023, 01, 15)));
        people.add(new Person(2, LocalTime.of(1, 45), LocalDate.of(2023, 03, 06)));
        people.add(new Person(3, LocalTime.of(4, 15), LocalDate.of(2023, 05, 27)));
        people.add(new Person(4, LocalTime.of(2, 30), LocalDate.of(2023, 06, 16)));
        people.add(new Person(5, LocalTime.of(1, 00), LocalDate.of(2023, 10, 22)));
        people.add(new Person(6, LocalTime.of(10, 30), LocalDate.of(2023, 10, 30)));
        people.add(new Person(7, LocalTime.of(3, 20), LocalDate.of(2023, 11, 15)));
        people.add(new Person(8, LocalTime.of(2, 35), LocalDate.of(2023, 11, 20)));
        people.add(new Person(9, LocalTime.of(11, 10), LocalDate.of(2023, 12, 21)));
        people.add(new Person(10, LocalTime.of(9, 00), LocalDate.of(2024, 01, 27)));

        return people;
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

}
