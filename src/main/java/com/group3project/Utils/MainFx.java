package com.group3project.Utils;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
// import java.util.*;
import com.group3project.Appointment.*;
import com.group3project.Registration.*;
// import com.group3project.Utils.*;
// import com.group3project.Patient_Doctor.*;
import com.group3project.Home.*;

public class MainFx extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader fxmlLoaderLoginUI = new FXMLLoader(getClass().getResource("../../../fxml/Login.fxml"));
		Parent luiRoot = fxmlLoaderLoginUI.load();
		LoginController loginCont = (LoginController) fxmlLoaderLoginUI.getController();
		Scene luiScene = new Scene(luiRoot);
		this.showUI(luiScene, StageStyle.DECORATED, "Login Is Required", false);

		FXMLLoader fxmlLoaderRegistration = new FXMLLoader(getClass().getResource("../../../fxml/RegistrationUI.fxml"));
		Parent registrationRoot = fxmlLoaderRegistration.load();
		RegistrationController registrationCont = (RegistrationController) fxmlLoaderRegistration.getController();
		Scene regiScene = new Scene(registrationRoot);

		FXMLLoader fxmlLoaderHomepage = new FXMLLoader(getClass().getResource("../../../fxml/Homepage.fxml"));
		Parent homepageRoot = fxmlLoaderHomepage.load();
		HomePageController homepageCont = (HomePageController) fxmlLoaderHomepage.getController();
		Scene homepageScene = new Scene(homepageRoot);

		FXMLLoader fxmlLoaderContactUsPage = new FXMLLoader(getClass().getResource("../../../fxml/ContactUsPage.fxml"));
		Parent contactUsRoot = fxmlLoaderContactUsPage.load();
		HomePageController contactPageCont = (HomePageController) fxmlLoaderContactUsPage.getController();
		Scene contactUsScene = new Scene(contactUsRoot);

		FXMLLoader fxmlLoaderPastAppointments = new FXMLLoader(getClass().getResource("../../../fxml/PastAppointments.fxml"));
		Parent pastAppointmentsRoot = fxmlLoaderPastAppointments.load();
		PastAppointmentsController pastAppointmentsCont = (PastAppointmentsController) fxmlLoaderPastAppointments
				.getController();
		Scene pastAppointmentsScene = new Scene(pastAppointmentsRoot);

		FXMLLoader fxmlLoaderAppointments = new FXMLLoader(getClass().getResource("../../../fxml/Appointment.fxml"));
		Parent appointmentsRoot = fxmlLoaderAppointments.load();
		AppointmentController appointmentsCont = (AppointmentController) fxmlLoaderAppointments
				.getController();
		Scene appointmentsScene = new Scene(appointmentsRoot);

		loginCont.setRegistrationScene(regiScene);
		registrationCont.setLoginScene(luiScene);
		registrationCont.setHomepageScene(homepageScene);
		loginCont.setHomepageScene(homepageScene);
		homepageCont.setLoginScene(luiScene);
		homepageCont.setContactUsScene(contactUsScene);
		homepageCont.setAppointmentScene(appointmentsScene);
		homepageCont.setPastAppointsScene(pastAppointmentsScene);
		contactPageCont.setHomepageScene(homepageScene);
		pastAppointmentsCont.setHomepageScene(homepageScene);
		appointmentsCont.setHomepageScene(homepageScene);

	}

	public void showUI(Scene scene, StageStyle stageStyle, String title, boolean resizable) {
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle(title);

		stage.initStyle(stageStyle);

		stage.setResizable(resizable);

		stage.show();
	}

	public static void updateUI(Stage stage, String title, boolean resizable) {
		stage.setTitle(title);
		stage.setResizable(resizable);
	}

	public static void main(String[] args) {
		Application.launch(args);

	}
}
