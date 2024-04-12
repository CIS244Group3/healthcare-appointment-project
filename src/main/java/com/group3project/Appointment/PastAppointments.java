package com.group3project.Appointment;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PastAppointments extends Application {
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Past Doctor Appointments");
		String[] pastAppointments = {
				"2023-12-01", "10:00 AM",
				"2023-12-15", "1:30 PM",
				"2023-07-08", "11:15 AM",
				"2023-05-04", "3:00 PM",
				"2023-09-01", "9:00 AM",
				"2023-02-25", "2:30 PM",
				"2023-03-20", "10:45 AM"
		};

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(10);

		Label[] appointmentLabels = new Label[7];
		for (int i = 0; i < appointmentLabels.length; i++) {
			appointmentLabels[i] = new Label("Appointment " + (i + 1) + ":Date and Time");
			GridPane.setConstraints(appointmentLabels[i], 0, i);
			grid.getChildren().add(appointmentLabels[i]);
		}

		Button backButton = new Button("Back to Login ");
		backButton.setOnAction(e -> {
			System.out.println("Back to login page");
		});
		GridPane.setConstraints(backButton, 0, 8);
		grid.getChildren().add(backButton);

		Scene scene = new Scene(grid, 300, 200);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
