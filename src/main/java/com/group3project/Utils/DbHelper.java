package com.group3project.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DbHelper {

	private static String dbtype = ProjUtil.getProperty("db.type").toLowerCase();

	public DbHelper() {
	}

	public static String getDbtype() {
		return dbtype;
	}

	public Connection getConnection() {
		System.out.println("SHould print first");
		System.out.println(dbtype);

		Connection conn = null;
		try {

			if (dbtype.equals("sqlite")) {
				String url = "jdbc:sqlite:" + ProjUtil.getResourcePath(ProjUtil.getProperty("sqlite.dbname"));
				conn = DriverManager.getConnection(url);
			} else {

				Class.forName(ProjUtil.getProperty("db.driver"));
				System.out.println("Going in " + ProjUtil.getProperty("db.url"));

				conn = DriverManager.getConnection(ProjUtil.getProperty("db.url"));
				System.out.println("SHould print second");
				System.out.println(conn);

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return conn;
	}

	public void closeConnection(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
				System.out.println("I'm closing him up!");

			}

		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("Made in hereerererer");

			System.out.println(e.getMessage());
		}
	}

	public void execute(String sql) throws SQLException {
		Connection conn = null;
		try {
			conn = this.getConnection();
			Statement stmt = conn.createStatement();

			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	public ResultSet executeQuery(Connection conn, String sql) {

		ResultSet rs = null;
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return rs;
	}

	public ArrayList<String> getColumnNames(ResultSet rs) {
		ArrayList<String> al = new ArrayList<String>();

		try {

			ResultSetMetaData rsMetaData;
			rsMetaData = rs.getMetaData();

			// Retrieving the list of column names
			int count = rsMetaData.getColumnCount();
			for (int i = 1; i <= count; i++) {
				al.add(rsMetaData.getColumnName(i));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return al;
	}

	public static void main(String[] args) {
		// DbHelper test = new DbHelper();
		// Connection conn = test.getConnection();
		// test.closeConnection(conn);

	}
}
