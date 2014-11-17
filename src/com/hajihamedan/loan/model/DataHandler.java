package com.hajihamedan.loan.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataHandler {
	/**
	 * Use singleton pattern. This class use for connecting and execute queries.
	 */
	private static DataHandler instance;
	private final String jdbcUrl = "jdbc:oracle:thin:@localhost";
	private final String username = "admin";
	private final String password = "123456";
	private static Connection conn;
	private Statement statement;

	private DataHandler() throws ClassNotFoundException, SQLException {
		// Load oracle driver.
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// Get connection to database
		conn = DriverManager.getConnection(jdbcUrl, username, password);
	}

	public static synchronized DataHandler getInstance() throws ClassNotFoundException, SQLException {
		if (instance == null) {
			instance = new DataHandler();
		}
		return instance;
	}

	public void closeConnection() throws Exception {
		if (statement != null) {
			statement.close();
		}
	}

	public ResultSet query(String query) throws Exception {
		/**
		 * Give String argument as a query and execute that. Return result as
		 * ResultSet object.
		 */

		System.out.println(query);

		statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet result = statement.executeQuery(query);
		return result;
	}

	public ResultSet select(String tableName) throws Exception {
		String query = "SELECT * FROM " + tableName;
		return this.query(query);
	}

	public ResultSet select(String tableName, String orderColumn, String order) throws Exception {
		String query = "SELECT * FROM " + tableName + " ORDER BY " + orderColumn + " " + order;
		return this.query(query);
	}

	public ResultSet selectByCondition(String tableName, String condition) throws Exception {
		String query = "SELECT * FROM " + tableName + " WHERE " + condition;
		return this.query(query);
	}

	public ResultSet selectByCondition(String tableName, String condition, String orderColumn, String order) throws Exception {
		String query = "SELECT * FROM " + tableName + " WHERE " + condition;
		if (orderColumn != null && orderColumn != "" && order != null && order != "") {
			query += " ORDER BY " + orderColumn + " " + order;
		}
		return this.query(query);
	}

	public ResultSet selectMax(String tableName, String column) throws Exception {
		String query = "SELECT MAX(" + column + ") as " + column + " FROM " + tableName;
		return this.query(query);
	}

	public ResultSet insert(String tableName, String[] columns, String[] values) throws Exception {
		String query = "INSERT INTO " + tableName + " (";
		for (String column : columns) {
			query += column + ",";
		}
		query = query.substring(0, (query.length() - 1));
		query += ") VALUES (";
		for (String value : values) {
			query += value + ",";
		}
		query = query.substring(0, (query.length() - 1));
		query += ")";

		return this.query(query);
	}

	public ResultSet update(String tableName, String[] columns, String[] values, int idIndex) throws Exception {

		String query = "UPDATE " + tableName + " SET ";
		for (int i = 0; i < columns.length; i++) {
			query += columns[i] + "=" + values[i] + ",";
		}
		query = query.substring(0, (query.length() - 1));
		query += " WHERE " + columns[idIndex] + "=" + values[idIndex];

		return this.query(query);
	}

	public ResultSet delete(String tableName, String condition) throws Exception {
		String query = "DELETE FROM " + tableName + " WHERE " + condition;
		return this.query(query);
	}
}