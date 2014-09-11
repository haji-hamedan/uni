package com.hajihamedan.loan.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DataHandler {
	/**
	 * Use singleton pattern.
	 * This class use for connecting and execute queries.
	 */
	private static DataHandler instance;
	private String jdbcUrl = "jdbc:oracle:thin:@localhost";
	private String username = "admin";
	private String password = "admin";
	private static Connection conn;
	private Statement statement;

	private DataHandler() {
		try {
			try {
				// Load oracle driver.
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				System.out.println("Oracle driver not found!");
			}
			// Get connection to database
			conn = DriverManager.getConnection(jdbcUrl, username, password);
		} catch (SQLException e) {
			System.out.println("Connection has not been established!");
		}
	}

	public static synchronized DataHandler getInstance() {
		if (instance == null) {
			instance = new DataHandler();
		}
		return instance;
	}

	public ResultSet query(String query) throws SQLException {
		/**
		 * Give String argument as a query and execute that. Return result as
		 * ResultSet object.
		 */
		statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		ResultSet result = statement.executeQuery(query);
		return result;
	}

	public ResultSet select(String tableName) throws SQLException{
		String query = "SELECT * FROM "+tableName;
		return this.query(query);
	}
	
	public ResultSet insert(String tableName, String[] columns, String[] values) throws SQLException{
		String query = "INSERT INTO "+tableName + "(";
		for (String column : columns){
			query += column + ",";
		}
		query = query.substring(0, (query.length()-1));
		query += ") VALUES ("; 
		for (String value : values){
			query += value + ",";
		}
		query = query.substring(0, (query.length()-1));
		query += ")";
		return this.query(query);
	}

}