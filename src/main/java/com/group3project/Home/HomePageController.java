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

import com.group3project.Appointment.AppointmentController;
import com.group3project.Appointment.PastAppointmentsController;
import com.group3project.Patient_Doctor.Patient;
import com.group3project.Patient_Doctor.UserProfileController;
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
        }
        openNewScene(event, this.userProfileScene, "User Profile", false);
    }

    public void onHomeButtonClick(ActionEvent event) throws IOException {
        // openNewScene(event, this.homepageScene, "Homepage", false);
        // Node node = (Node) event.getSource();
        // Stage stage = (Stage) node.getScene().getWindow();
        // Patient patient = (Patient) stage.getUserData();

        // System.out.println(patient.toString());
        System.out.println(this.currentUser.getName());
    }

    public void onPastAppointmentButtonClick(ActionEvent event) throws IOException {
        if (this.pastAppointmentScene == null) {
            FXMLLoader fxmlLoaderPastAppointments = new FXMLLoader(
                    getClass().getResource("../../../fxml/PastAppointments.fxml"));
            Parent pastAppointmentsRoot = fxmlLoaderPastAppointments.load();
            PastAppointmentsController pastAppointmentsCont = (PastAppointmentsController) fxmlLoaderPastAppointments
                    .getController();

            Scene pastAppointmentsScene = new Scene(pastAppointmentsRoot);
            setPastAppointsScene(pastAppointmentsScene);
            setCurrentPastAppointmentCont(pastAppointmentsCont);
            currentPastAppointmentsCont.setCurrentUser(this.currentUser);
            currentPastAppointmentsCont.setHomepageScene(((Node) event.getSource()).getScene());
        }
        openNewScene(event, this.pastAppointmentScene, "Past Appointments", false);

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

}
