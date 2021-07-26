package database;

import java.sql.*;
import java.util.HashMap;

/**
 * A program to record new and update existing working time records
 * 
 * @author Simon Valiente
 * @version 2.0
 */

public class AddWorkingTime {

	private Connection connection; // Erstellt ein Objekt der Klasse Connection
	private String workDate;
	private int MA_ID;

	/**
	 * Constructor
	 * 
	 * Calls a method to establish a connection to the database and defines its behavior after closing the program. 
	 *
	 * @param workDate date of working day in yyyy-mm-dd
	 * @param MA_ID individual employee identification number in int
	 *
	 * @see createConnection()
	 */
	public AddWorkingTime(String workDate, int MA_ID) {
		this.workDate = workDate;
		this.MA_ID = MA_ID;
		createConnection();
		Thread shutDownHook = new Thread() {
			public void run() {
				System.out.println("Running shutdown hook");
				if (connection == null)
					System.out.println("Connection to database already closed");
				try {
					if (connection != null && !connection.isClosed()) {
						connection.close();
						if (connection.isClosed())
							System.out.println("Connection to database closed");
					}
				} catch (SQLException e) {
					System.err.println("Shutdown hook couldn't close database connection.");
				}
			}
		};
		Runtime.getRuntime().addShutdownHook(shutDownHook);
	}

	/**
	 * Establish connection to MySQL driver
	 * Remark: if connection fails, check if CLASSPATH variable is set correctly.
	 */

	static {
		try {
			Class<?> c = Class.forName("com.mysql.cj.jdbc.Driver");
			if (c != null) {
				System.out.println("JDBC-Treiber geladen");
			}
		} catch (ClassNotFoundException e) {
			System.err.println("Fehler beim Laden des JDBC-Treibers");
			System.exit(1);
		}
	}

	/**
	 * Establish connection to the database
	 */

	private void createConnection() {
		String url = "jdbc:mysql://localhost/zeiterfassung";
		String user = "root";
		String pass = "1234";
		try {
			System.out.println("Creating DBConnection");
			connection = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			System.err.println("Couldn't create DBConnection");
			System.exit(1);
		}
	}

	/**
	 * Adds new record of working day information to database
	 * Creates MySQL command and passes it to database, where it will be executed.
	 *
	 * @param beginTime  is the begin of working time at a working days in hh:mm:ss
	 * @param endTime    is the end of working time at a working days in hh:mm:ss
	 * @param totalBreak is the total break duration at a working day in hh:mm:ss
	 * @param overtime   is the amount of working time which exceeds the individual planned working time of employee 
	 * 					 in hh:mm:ss
	 * @param comment    is an additional information / comment for the working time record
	 * @throws BatchUpdateException when record for selected employee and date already exists (remark: is handled external)
	 * 
	 * @return feedback, if record was successfully created
	 */

	public boolean addWorkingTime(String beginTime, String endTime, String totalBreak, String overtime, String comment)
			throws BatchUpdateException {
		String query = "INSERT INTO zeitkonto VALUES ('" // Neuer Eintrag wird angelegt
				+ workDate + "', " + MA_ID + ", '" + beginTime + "', '" + endTime + "', '" + totalBreak + "', '"
				+ overtime + "', '" + comment + "')";

		Statement stmt = null;
		try {
			if (connection.isClosed()) {
				createConnection();
			}
			connection.setAutoCommit(false); // Das Statement
			stmt = connection.createStatement();
			stmt.addBatch(query);
			stmt.executeBatch();
			connection.commit();
			stmt.close();
			System.out.println("Eintrag erfolgreich angelegt");
			return true;
		} catch (BatchUpdateException e) {
			throw new BatchUpdateException();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
			}
		}
		return false;
	}

	/**
	 * Updates (overwrites) an existing working time record for a selected employee
	 * Warning: If record does not exist, there won't be any action, error messages nor exceptions. The MySQL command
	 * will be created and passed to the database where it will be executed. 
	 *
	 * @param beginTime  is the begin of working time at a working days in hh:mm:ss
	 * @param endTime    is the end of working time at a working days in hh:mm:ss
	 * @param totalBreak is the total break duration at a working day in hh:mm:ss
	 * @param overtime   is the amount of working time which exceeds the individual planned working time of employee 
	 * 					 in hh:mm:ss
	 * @param comment    is an additional information / comment for the working time record
	 * 
	 * @return feedback, if record was successfully updated
	 */

	public boolean modifyWorkingTime(String beginTime, String endTime, String totalBreak, String overtime,
			String comment) {
		String query = "UPDATE zeitkonto " // Eintrag bzw. Einträge werden überarbeitet
				+ "SET Arbeitszeit_Beginn = '" + beginTime + "', " + "Arbeitszeit_Ende = '" + endTime + "', "
				+ "Pausengesamtzeit_Tag = '" + totalBreak + "', " + "Ueberstunden_Tag = '" + overtime + "', "
				+ "Kommentar = '" + comment + "' " + "WHERE work_date = '" + workDate + "' AND " // unter angegebenen
																									// Bedingungen
				+ "MA_ID = " + MA_ID;

		Statement stmt = null;
		try {
			if(connection.isClosed()) {
				createConnection();
			}
			connection.setAutoCommit(false); // Das Statement
			stmt = connection.createStatement();
			stmt.addBatch(query);
			stmt.executeBatch();
			connection.commit();
			stmt.close();
			System.out.println("Eintrag erfolgreich geändert");
			return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
			}
		}
		return false;
	}

	/**
	 * Selects work time data from database when a possible double entry of the same working day occurs to warn the user
	 * about an possible overwrite if his/her data. 
	 * Selected data will be displayed at user interfase and the user is asked to compare the entry data (new and 
	 * existing) and decide how to proceed.
	 * 
	 * @return collection of working time data
	 */
	public HashMap<String, String> getWorkingTime() {
		String query = "SELECT Arbeitszeit_Beginn, Arbeitszeit_Ende, Pausengesamtzeit_Tag, Ueberstunden_Tag, Kommentar "
				+ "FROM zeitkonto WHERE " + "work_date  = '" + workDate + "' AND " + "MA_ID = " + MA_ID;
		Statement stmt = null;
		HashMap<String, String> result = new HashMap<String, String>();

		try {
			if(connection.isClosed()) {
				createConnection();
			}
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			connection.commit();
			rs.next();
			result.put("Arbeitszeit_Beginn", rs.getString(1));
			result.put("Arbeitszeit_Ende", rs.getString(2));
			result.put("Pausengesamtzeit_Tag", rs.getString(3));
			result.put("Ueberstunden_Tag", rs.getString(4));
			result.put("Kommentar", rs.getString(5));
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
			}
		}
		return result;
	}

	/**
	 * Closes database connection
	 * 
	 * @return feedback, if closure succeded
	 */
	
	public boolean close() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
				if (connection.isClosed()) {
					System.out.println("Connection to Database is closed");
				}
			}
		} catch (SQLException e) {
			System.out.println("Couldn't close Connection to Database");
		}
		return true;
	}

}
