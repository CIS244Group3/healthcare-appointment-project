package com.group3project.Home;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;

import com.group3project.Appointment.AppointmentController;
import com.group3project.Appointment.PastAppointment;
import com.group3project.Appointment.PastAppointmentsController;
import com.group3project.Patient_Doctor.Patient;
import com.group3project.Patient_Doctor.UserProfileController;
import com.group3project.Utils.DbHelper;
import com.group3project.Utils.MainFx;

public class HomePageController {

    public ImageView appLogo;

    public void onContactUsButtonClick(ActionEvent event) throws IOException {
        if (this.contactUScene == null) {
            FXMLLoader fxmlLoaderContactUsPage = new FXMLLoader(
                    getClass().getResource("../../../fxml/ContactUsPage.fxml"));
            Parent contactUsRoot = fxmlLoaderContactUsPage.load();
            Scene contactUsScene = new Scene(contactUsRoot);
            ContactUsController contactPageCont = (ContactUsController) fxmlLoaderContactUsPage.getController();

            this.setCurrentContactUsCont(contactPageCont);
            this.setContactUsScene(contactUsScene);
            this.currentContactUsController.setHomepageScene(((Node) event.getSource()).getScene());
            this.currentContactUsController.setCurrentUser(this.currentUser);
        }
        openNewScene(event, this.contactUScene, "Contact Us", false);

    }

    public void onAppointmentButtonClick(ActionEvent event) throws IOException {
        if (this.appointmentScene == null) {
            FXMLLoader fxmlLoaderAppointments = new FXMLLoader(
                    getClass().getResource("../../../fxml/Appointment.fxml"));
            Parent appointmentsRoot = fxmlLoaderAppointments.load();
            AppointmentController appointmentCont = (AppointmentController) fxmlLoaderAppointments
                    .getController();
            Scene appointmentsScene = new Scene(appointmentsRoot);

            this.setAppointmentScene(appointmentsScene);
            this.setCurrentAppointmentController(appointmentCont);
            this.currentAppointmentController.setCurrentUser(this.currentUser);
            this.currentAppointmentController.setHomepageScene(((Node) event.getSource()).getScene());
        }
        openNewScene(event, this.appointmentScene, "Make an Appointment", false);

    }

    public void onPastAppointmentButtonClick(ActionEvent event) throws IOException {

        if (this.pastAppointmentScene == null) {

            FXMLLoader fxmlLoaderPastAppointments = new FXMLLoader(
                    getClass().getResource("../../../fxml/PastAppointments.fxml"));
            Parent pastAppointmentsRoot = fxmlLoaderPastAppointments.load();
            PastAppointmentsController pastAppointmentsCont = (PastAppointmentsController) fxmlLoaderPastAppointments
                    .getController();

            Scene pastAppointmentsScene = new Scene(pastAppointmentsRoot);

            this.setPastAppointsScene(pastAppointmentsScene);
            this.setCurrentPastAppointmentCont(pastAppointmentsCont);
            this.currentPastAppointmentsCont.setCurrentUser(this.currentUser);
            this.currentPastAppointmentsCont.setUpTableView();

            this.currentPastAppointmentsCont.setHomepageScene(((Node) event.getSource()).getScene());
        }
        openNewScene(event, this.pastAppointmentScene, "Past Appointments", false);

    }

    public void onProfileButtonClick(ActionEvent event) throws IOException {
        if (this.userProfileScene == null) {
            FXMLLoader fxmlLoaderUserProfile = new FXMLLoader(
                    getClass().getResource("../../../fxml/UserProfileUI.fxml"));
            Parent userProfileRoot = fxmlLoaderUserProfile.load();
            UserProfileController userProfileController = (UserProfileController) fxmlLoaderUserProfile
                    .getController();

            Scene userProfileUIScene = new Scene(userProfileRoot);
            setUserProfileScene(userProfileUIScene);
            setCurrentuserProfileController(userProfileController);
            currentUserProfileController.setCurrentUser(this.currentUser);
            currentUserProfileController.setHomepageScene(((Node) event.getSource()).getScene());
            currentUserProfileController.setUserInfo();
            currentUserProfileController.populateUserInfo();

        }
        openNewScene(event, this.userProfileScene, "User Profile", false);
    }

    @FXML
    void onLogOutClick(ActionEvent event) throws IOException {
        logOutScene(event);
    }

    @FXML
    private Button btnAppointment;

    @FXML
    private Button btnContactUs;

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

    private PastAppointmentsController currentPastAppointmentsCont;

    private ContactUsController currentContactUsController;

    private AppointmentController currentAppointmentController;

    private UserProfileController currentUserProfileController;

    public void setCurrentUser(Patient user) {
        this.currentUser = user;
    }

    private Scene contactUScene;

    public void setContactUsScene(Scene scene) {
        this.contactUScene = scene;
    }

    public void setCurrentContactUsCont(ContactUsController controller) {
        this.currentContactUsController = controller;
    }

    public void setCurrentPastAppointmentCont(PastAppointmentsController controller) {
        this.currentPastAppointmentsCont = controller;
    }

    public void setCurrentAppointmentController(AppointmentController controller) {
        this.currentAppointmentController = controller;
    }

    private Scene pastAppointmentScene;

    public void setPastAppointsScene(Scene scene) {
        this.pastAppointmentScene = scene;
    }

    private Scene appointmentScene;

    public void setAppointmentScene(Scene scene) {
        this.appointmentScene = scene;
    }

    private Scene userProfileScene;

    public void setUserProfileScene(Scene scene) {
        this.userProfileScene = scene;
    }

    public void setCurrentuserProfileController(UserProfileController controller) {
        this.currentUserProfileController = controller;
    }

    public void openNewScene(ActionEvent actionEvent, Scene scene, String title, boolean resizable) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        MainFx.updateUI(primaryStage, title, resizable);
    }

    void logOutScene(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoaderLoginUI = new FXMLLoader(getClass().getResource("../../../fxml/Login.fxml"));
        Parent luiRoot = fxmlLoaderLoginUI.load();
        Scene luiScene = new Scene(luiRoot);
        openNewScene(event, luiScene, "Login Is Required", false);

    }

    private ObservableList<PastAppointment> getAppointments() {
        ObservableList<PastAppointment> currentAppointments = FXCollections.observableArrayList();
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
                if (date.isAfter(LocalDate.now()) || date.isEqual(LocalDate.now())) {
                    currentAppointments.add(new PastAppointment(date, time, firstName, lastName, specialty));
                }
            }

            conn.close();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return currentAppointments;

    }

    public void setUpTableView() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<PastAppointment, LocalDate>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<PastAppointment, LocalTime>("time"));
        doctorColumn.setCellValueFactory(new PropertyValueFactory<PastAppointment, String>("doctor"));
        specialtyColumn.setCellValueFactory(new PropertyValueFactory<PastAppointment, String>("specialty"));

        tableView.setItems(getAppointments());
    }

}
