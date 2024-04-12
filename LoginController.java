package com.group3project.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

	@FXML // fx:id="btnCancel"
	private Button btnCancel; // Value injected by FXMLLoader

	@FXML // fx:id="btnLogin"
	private Button btnLogin; // Value injected by FXMLLoader

	@FXML // fx:id="pwfPassword"
	private PasswordField pwfPassword; // Value injected by FXMLLoader

	@FXML // fx:id="txfUsername"
	private TextField txfUsername; // Value injected by FXMLLoader

	@FXML
	void onCancel(ActionEvent event) {

	}

	private EventHandler<ActionEvent> ehae = null;
	Map<String, String> userInfo = null;

	private boolean checkedUser(String email, String password) {

		boolean status = false;

		try {
			String sql = "SELECT * FROM users WHERE password = ? AND email = ? ";

			DbHelper dal = new DbHelper();

			Connection conn = dal.getConnection();

			PreparedStatement pStmt = conn.prepareStatement(sql);

			// Set Parameters
			pStmt.setString(1, ProjUtil.getSHA(password));
			pStmt.setString(2, email);

			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				this.userInfo = new HashMap<>();
				this.userInfo.put("first_name", rs.getString("first_name"));
				this.userInfo.put("last_name", rs.getString("last_name"));
				this.userInfo.put("phone", rs.getString("phone"));
				this.userInfo.put("email", email);

				status = true;
			} else {
				this.userInfo = null;
			}

			conn.close();

		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}

		return status;
	}

	private boolean isValidLogin() {
		String username = this.txfUsername.getText();
		String password = this.pwfPassword.getText();

		if (username.length() == 0) {
			ProjUtil.showAlert("Username is required input");
			return false;
		} else if (this.pwfPassword.getText().length() == 0) {
			ProjUtil.showAlert("Password is required input");
			return false;
		} else {
			// check login against the database
			this.txfUsername.setText("");
			this.pwfPassword.setText("");
			return this.checkedUser(username, password);
		}

	}

	@FXML
	void onLogin(ActionEvent event) {

		if (this.isValidLogin()) {

			Stage stage = (Stage) btnLogin.getScene().getWindow();
			stage.close();

			if (this.ehae != null) {
				this.ehae.handle(event);
			}
		} else {
			ProjUtil.showAlert("Invalid credentials");
		}
	}

	public void setOnAction(EventHandler<ActionEvent> ehae) {
		this.ehae = ehae;
	}

	public Map<String, String> getUserInfo() {
		return this.userInfo;
	}
}
