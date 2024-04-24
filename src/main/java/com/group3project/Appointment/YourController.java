import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class YourController implements Initializable {

    @FXML
    private ComboBox<Doctor> comboBox; // Assuming you already have this defined in your FXML

    @FXML
    private AnchorPane anchorPane; // Reference to your existing AnchorPane in FXML

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

            comboBox.setItems(doctors);

            // Create a label to display doctor's id
            Label idLabel = new Label();
            anchorPane.getChildren().add(idLabel);

            // Add listener to ComboBox selection property
            comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    idLabel.setText("Doctor ID: " + newValue.getId());

                    // Set label position within AnchorPane (adjust as needed)
                    AnchorPane.setTopAnchor(idLabel, 20.0); // Example top margin
                    AnchorPane.setLeftAnchor(idLabel, 20.0); // Example left margin
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
