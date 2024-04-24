import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
public class Main extends Application {

    // Custom class to represent doctors
    public static class Doctor {
        private int id;
        private String name;

        public Doctor(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // Connect to the database (replace with your database URL, username, and password)
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "username", "password");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, name FROM doctors")) {

            ObservableList<Doctor> doctors = FXCollections.observableArrayList();

            // Populate the doctors list from the ResultSet
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                doctors.add(new Doctor(id, name));
            }

            // Create the ComboBox
            ComboBox<Doctor> comboBox = new ComboBox<>(doctors);

            // Create a label to display doctor's id
            Label idLabel = new Label();

            // Add listener to ComboBox selection property
            comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    idLabel.setText("Doctor ID: " + newValue.getId());
                }
            });

            VBox root = new VBox(10);
            root.getChildren().addAll(comboBox, idLabel);
            Scene scene = new Scene(root, 300, 200);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Doctor ComboBox Example");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}