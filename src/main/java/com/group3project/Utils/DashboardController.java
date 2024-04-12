package com.group3project.Utils;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

public class DashboardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane btnClear;

    @FXML
    private Button btnGo;

    @FXML
    private Button btnSave;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private TextField txtUrl;

    @FXML
    private WebView webView;

    @FXML
    void onClear(ActionEvent event) {

    }

    @FXML
    void onSave(ActionEvent event) {

    }

    @FXML
    void onGo(ActionEvent event) {
        String url = this.txtUrl.getText().trim();

        // Check if the URL is empty
        if (url.isEmpty()) {
            ProjUtil.showAlert("URL is empty");
            return;
        }

        // Add protocol if missing
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }

        this.txtUrl.setText(url);
        try {
            // Load the URL in the WebView
            webView.getEngine().load(url);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
