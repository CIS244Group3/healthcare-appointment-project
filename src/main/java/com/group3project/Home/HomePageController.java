package com.group3project.Home;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;

import org.json.JSONObject;

import com.group3project.Appointment.AppointmentController;
import com.group3project.Appointment.ChangeAppointmentController;
import com.group3project.Appointment.PastAppointment;
import com.group3project.Appointment.PastAppointmentsController;
import com.group3project.Patient_Doctor.Patient;
import com.group3project.Patient_Doctor.UserProfileController;
import com.group3project.Utils.DbHelper;
import com.group3project.Utils.MainFx;
import com.group3project.Utils.ProjUtil;

public class HomePageController {

    public ImageView appLogo;
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final String API_KEY = ProjUtil.getProperty("ai.key");
    private static final String API_URL = ProjUtil.getProperty("ai.url");
    private static final String MODEL = ProjUtil.getProperty("ai.model");

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
            this.currentAppointmentController.setHomePageController(this);
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
    private Button cancelAppointment;

    @FXML
    private Button changeAppointment;

    @FXML
    private TextField userInput;

    @FXML
    private TextArea chatArea;

    @FXML
    private Button sendButton;

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
    private PastAppointment selectedAppointment;

    @FXML
    private HBox editSection;

    private BooleanProperty allowEditSection = new SimpleBooleanProperty(false);

    private BooleanProperty hideChange = new SimpleBooleanProperty(false);

    public void showEditSetion() {
        this.allowEditSection.set(true);
    }

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

    private Scene changeAppointmentScene;

    public void setChangeAppointmentScene(Scene scene) {
        this.changeAppointmentScene = scene;
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

    public TableView<PastAppointment> getTableView() {
        return this.tableView;
    }

    // ChatGPT Integration

    @FXML
    void onSendButtonClick(ActionEvent event) {
        String inputText = userInput.getText();
        if (inputText != null && !inputText.trim().isEmpty()) {
            CompletableFuture.supplyAsync(() -> getResponse(inputText))
                    .thenAccept(response -> Platform
                            .runLater(() -> chatArea.appendText("You: " + inputText + "\nBot: " + response + "\n")))
                    .exceptionally(ex -> {
                        Platform.runLater(() -> chatArea.appendText("Error: " + ex.getMessage() + "\n"));
                        return null;
                    });
            userInput.clear();
        }
    }

    private String getResponse(String inputText) {
        System.out.println(API_KEY);
        String jsonBody = "{"
                + "\"model\": \"" + MODEL + "\","
                + "\"messages\": [{\"role\": \"user\", \"content\": \"" + inputText + "\"}],"
                + "\"max_tokens\": 150,"
                + "\"temperature\": 0.5"
                + "}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(java.net.URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .header("OpenAI-Beta", "assistants=v2") // Include the beta header as specified
                .POST(java.net.http.HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        try {
            java.net.http.HttpResponse<String> response = client.send(request,
                    java.net.http.HttpResponse.BodyHandlers.ofString());
            JSONObject jsonResponse = new JSONObject(response.body());
            System.out.println(jsonResponse + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            String chatResponse = jsonResponse.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");
            return chatResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing your request.";
        }
    }

    private ObservableList<PastAppointment> getAppointments() {
        ObservableList<PastAppointment> currentAppointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT appointment.id, appointment.appointmentdate, appointment.appointmenttime, doctor.firstname, doctor.lastname, doctor.specialty FROM appointment JOIN doctor ON appointment.doctorid=doctor.id WHERE patientid=?";

            DbHelper dal = new DbHelper();

            Connection conn = dal.getConnection();

            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, this.currentUser.getId());

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                LocalDate date = rs.getDate("appointmentdate").toLocalDate();
                LocalTime time = rs.getTime("appointmenttime").toLocalTime();
                int id = rs.getInt("id");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String specialty = rs.getString("specialty");
                if (date.isAfter(LocalDate.now()) || date.isEqual(LocalDate.now())) {
                    currentAppointments.add(new PastAppointment(id, date, time, firstName, lastName, specialty));
                }
            }

            conn.close();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return currentAppointments;

    }

    @FXML
    void handleRowClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            PastAppointment selectAppointment = tableView.getSelectionModel().getSelectedItem();
            if (selectAppointment != null) {
                this.selectedAppointment = selectAppointment;
                this.allowEditSection.set(true);
            }
        }
    }

    // DELETE FROM `patients` WHERE `patients`.`id` = 2
    @FXML
    void handleCancel(ActionEvent event) {
        cancelAppointment();
    }

    @FXML
    void handleChange(ActionEvent event) {

    }

    // void changeTimePopUp(ActionEvent event, Patient patient) throws Exception {

    // FXMLLoader fxmlLoaderChangeAppointment = new FXMLLoader(
    // getClass().getResource("../../../fxml/ChangeAppointment.fxml"));
    // Parent changeAppointmentRoot = fxmlLoaderChangeAppointment.load();
    // ChangeAppointmentController changeAppointmentCont =
    // (ChangeAppointmentController) fxmlLoaderChangeAppointment
    // .getController();
    // this.changeAppointmentScene = new Scene(changeAppointmentRoot);
    // changeAppointmentCont.setCurrentUser(this.currentUser);
    // changeAppointmentCont.setCurrentDoctorId(this.selectedAppointment.getId());

    // openLoginScene(event);
    // this.showUI(this.oneTimeCodeScene, StageStyle.DECORATED, "Enter One Time
    // Code", false);

    // }

    private void cancelAppointment() {
        String update = "DELETE FROM appointment WHERE id = ?";
        try (Connection conn = new DbHelper().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(update)) {
            pstmt.setInt(1, this.selectedAppointment.getId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                showAlertDialog(Alert.AlertType.INFORMATION, "Cancellation Successful",
                        "Please go to Appointments to schedule another");
                this.allowEditSection.set(false);
                tableView.getItems().remove(this.selectedAppointment);
                this.selectedAppointment = null;
            } else {
                showAlertDialog(Alert.AlertType.ERROR, "Cancellation Failed", "Please try again");
            }
        } catch (SQLException e) {
            showAlertDialog(Alert.AlertType.ERROR, "Database Error", "Error updating user profile: " + e.getMessage());
        }
    }

    // private void updateAppointment() {
    // String update = "UPDATE appointment SET appointmentdate = ?, appointmenttime
    // = ? WHERE id=?";
    // try (Connection conn = new DbHelper().getConnection();
    // PreparedStatement pstmt = conn.prepareStatement(update)) {
    // pstmt.setInt(1, this.selectedAppointment.getId());

    // int affectedRows = pstmt.executeUpdate();
    // if (affectedRows > 0) {
    // showAlertDialog(Alert.AlertType.INFORMATION, "Update Successful",
    // "Appointment change has been made");
    // this.allowEditSection.set(false);
    // tableView.getItems().remove(this.selectedAppointment);
    // tableView.getItems()
    // .add(new PastAppointment(this.selectedAppointment.getId(), null, null,
    // update, update, update));

    // this.selectedAppointment = null;
    // } else {
    // showAlertDialog(Alert.AlertType.ERROR, "Cancellation Failed", "Please try
    // again");
    // }
    // } catch (SQLException e) {
    // showAlertDialog(Alert.AlertType.ERROR, "Database Error", "Error updating user
    // profile: " + e.getMessage());
    // }
    // }

    public void setUpTableView() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<PastAppointment, LocalDate>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<PastAppointment, LocalTime>("time"));
        doctorColumn.setCellValueFactory(new PropertyValueFactory<PastAppointment, String>("doctor"));
        specialtyColumn.setCellValueFactory(new PropertyValueFactory<PastAppointment, String>("specialty"));

        tableView.setItems(getAppointments());
    }

    private void showAlertDialog(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void initialize() {
        editSection.visibleProperty().bind(allowEditSection);
        changeAppointment.visibleProperty().bind(hideChange);

    }

}
