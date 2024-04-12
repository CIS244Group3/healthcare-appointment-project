package com.group3project.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainDemo {

	public static void main(String[] args) {
		// Uncomment each of the method below to test for its usage
		// demoSHA();
		// demoSql();
		demoUserSql();
		// demoAES();
	}

	static void demoSHA() {
		System.out.println("   my secret: " + ProjUtil.getSHA("my secret"));
		System.out.println("Hello_Cis244: " + ProjUtil.getSHA("Hello_Cis244"));
		System.out.println(" my hashed password1: " + ProjUtil.getSHA("password123"));
		System.out.println(" my hashed password2: " + ProjUtil.getSHA("Password123"));
		System.out.println("my original password: " + ProjUtil.getSHA("password123"));
	}

	static void demoAES() {
		String msg = "Hello CIS244 students\nPlease complete the following tasks and fill in your logics to complete the project";

		System.out.println(" Original message: " + msg);

		String encryptedMsg = ProjUtil.encrypt(msg);
		System.out.println("Encrypted message: " + encryptedMsg);

		String decryptedMsg = ProjUtil.decrypt(encryptedMsg);
		System.out.println("Decrypted message: " + decryptedMsg);

	}

	static void demoUserSql() {

		try {
			// String password = ProjUtil.getSHA("1234");
			String password = "klasdjngl;kjhasdlkgjnasd@kjh5";
			String email = "kglasper7@student.ccc.edu";

			String sql = "SELECT * FROM patients WHERE password = ? AND email = ? ";

			DbHelper dal = new DbHelper();

			Connection conn = dal.getConnection();

			PreparedStatement pStmt = conn.prepareStatement(sql);
			//
			// // Set Parameters
			pStmt.setString(1, password);
			pStmt.setString(2, email);

			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				System.out.println("Found record");

				System.out.println("email: " + rs.getString("email"));
				System.out.println("password: " + rs.getString("password"));
				System.out.println("phone number: " + rs.getString("phonenumber"));
			} else {
				System.out.println("Not found record");
			}

		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}

	}

	static void demoSql() {
		System.out.println(DbHelper.getDbtype());
		demoExecuteQuery("SELECT * FROM customers LIMIT 50");

		// demoExecuteQuery("SELECT *" + "\n" +
		// "FROM customers " + "\n" +
		// "WHERE country = 'Mexico' " + "\n" +
		// "ORDER BY address desc" );
		// demoPreparedStatementExecuteQuery("ANATR");

	}

	static void demoExecuteQuery(String sql) {

		DbHelper dal = new DbHelper();

		Connection conn = dal.getConnection();

		ResultSet rs = dal.executeQuery(conn, sql);

		ArrayList<String> al = dal.getColumnNames(rs);
		for (String name : al) {
			System.out.println("field name: " + name);
		}

		try {
			while (rs.next()) {
				System.out.println(rs.getString("customerID") + "\t" + rs.getString("companyName") + "\t"
						+ rs.getString("contactName") + "\t" + rs.getString("contactTitle") + "\t"
						+ rs.getString("address") + "\t" + rs.getString("city") + "\t" + rs.getString("region") + "\t"
						+ rs.getString("postalCode") + "\t" + rs.getString("country") + "\t" + rs.getString("phone")
						+ "\t" + rs.getString("fax"));
				System.out.println("\n");

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		dal.closeConnection(conn);
	}

	static void demoPreparedStatementExecuteQuery(String customerId) {

		DbHelper dal = new DbHelper();

		Connection conn = dal.getConnection();

		try {

			String sql = "Delete From customers where customerId = ?";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			// Set Parameters
			pStmt.setString(1, customerId);

			pStmt.execute();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		dal.closeConnection(conn);

	}
}
