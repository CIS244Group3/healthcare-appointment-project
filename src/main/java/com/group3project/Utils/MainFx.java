package com.group3project.Utils;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
// import java.util.*;
// import com.group3project.Appointment.*;
// import com.group3project.Utils.*;
// import com.group3project.Patient_Doctor.*;

public class MainFx extends Application {

	private Image icon = new Image("https://storage.googleapis.com/innovare-poc/epoch-logo.png");

	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader fxmlLoaderLoginUI = new FXMLLoader(getClass().getResource("../../../fxml/Login.fxml"));
		Parent luiRoot = fxmlLoaderLoginUI.load();
		Scene luiScene = new Scene(luiRoot);
		this.showUI(luiScene, StageStyle.DECORATED, "Login Is Required", false);

	}

	public void showUI(Scene scene, StageStyle stageStyle, String title, boolean resizable) {
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle(title);

		stage.initStyle(stageStyle);
		stage.getIcons().add(this.icon);
		stage.setResizable(resizable);
		stage.setMaximized(resizable);
		stage.show();
	}

	public static void updateUI(Stage stage, String title, boolean resizable) {
		stage.setTitle(title);
		// stage.getIcons().add(logo);

		stage.setResizable(resizable);
		stage.setMaximized(resizable);

	}

	public static void main(String[] args) {
		Application.launch(args);

	}
}
