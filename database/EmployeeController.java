package database;

import java.sql.*;
import java.util.HashMap;

/**
 * Program to add, modify, view and delete employees in database.
 * 
 * @author Josephine Luksch
 * @author Simon Valiente
 * @author Julian Erxleben
 * @version 1.0
 */
public class EmployeeController {
	
	private Connection connection;
	
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
	 * Empty constructor
	 * Calls a method to establish a connection to the database and defines its behavior after closing the program.
	 * 
	 * @see createConnection()
	 */
	
	public EmployeeController() {
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
	 * Creates an employee based on the given data.
	 * 
	 * @param MA_ID individual employee's id in int
	 * @param firstName employee's first name
	 * @param lastName employee's last name
	 * @param address employee's address
	 * @param postcode employee's postcode as String
	 * @param city the city the employee is living in
	 * @param team the team in which the employee is working
	 * @param mail employee's e-mail address
	 * @param vacationDays number ov employee's vacation days per year as int
	 * @param plannedWorkingTime planned working time as String "hh:mm:ss"
	 * @param active notion if the employee is active, must be either "ja" or "nein"
	 * 
	 * @return feedback, if record was successfully created
	 */
	
	public boolean createEmployee(int MA_ID, String firstName, String lastName,
			String address, String postcode, String city, String team, 
			String mail, int vacationDays, String plannedWorkingTime, String active) {
		String query = "INSERT INTO mitarbeiter VALUES ("
				+ MA_ID + ", '" + firstName + "', '" + lastName + "', '"
				+ address + "', '" + postcode + "', '" + city + "', '" + team
				+ "', '" + mail + "', " + vacationDays + ", '" 
				+ plannedWorkingTime + "', '" + active + "')";
		Statement stmt = null;
		try {
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			stmt.addBatch(query);
			stmt.executeBatch();
			connection.commit();
			stmt.close();
			System.out.println("Eintrag erfolgreich angelegt");
			return true;
		} catch (SQLException e) {
			System.err.println("Eintrag konnte nicht angelegt werden."
					+ "\nDies sollte i.a. daran liegen, dass der Eintrag "
					+ "bereits existiert.");
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {}
		}
		return false;
	}
	
	/**
	 * Changes the employee's data
	 * 
	 * @param MA_ID the ID of the employee whose data needs to be changed
	 * @param changeData a HashMap with the new data. Not every database column
	 * needs to be part of {@code changeData}, but every key has to match one of
	 * the column's names
	 * @return feedback, if record was successfully modified
	 */
	
	public boolean modifyEmployee(int MA_ID, HashMap<String, String> changeData) {
		String query = "UPDATE mitarbeiter SET ";
		var keys = changeData.keySet();
		for (String key : keys) {
			query += key + " = '" + changeData.get(key) + "', ";
		}
		query = query.substring(0, query.length() - 2);
		query += " WHERE MA_ID = " + MA_ID;
		
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
			System.out.println("Eintrag erfolgreich ge�ndert");
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
	 * Deletes an employee's data. This includes their basic information, but also
	 * their login and working time data
	 * 
	 * @param MA_ID the id of the employee whose data needs to be deleted
	 * @return feedback, if record was successfully deleted
	 */
	
	public boolean deleteEmployee(int MA_ID) {
		String query0 = "DELETE FROM login WHERE MA_ID = " + MA_ID;
		String query1 = "DELETE FROM zeitkonto WHERE MA_ID = " + MA_ID;
		String query2 = "DELETE FROM mitarbeiter WHERE MA_ID = " + MA_ID;
		
		Statement stmt = null;
		try {
			if(connection.isClosed()) {
				createConnection();
			}
			connection.setAutoCommit(false); // Das Statement
			stmt = connection.createStatement();
			stmt.addBatch(query0);
			stmt.addBatch(query1);
			stmt.addBatch(query2);
			stmt.executeBatch();
			connection.commit();
			stmt.close();
			System.out.println("Eintrag erfolgreich gel�scht");
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
	 * Collects an employee's data and stores it in a HashMap
	 * 
	 * @param MA_ID the id of the employee whose data needs to be read
	 * @return a HashMap with the column names as its keys and the according
	 * values from the database as values
	 */
	
	public HashMap<String, String> getEmployeeData(int MA_ID){
		String query = "SELECT * FROM mitarbeiter WHERE MA_ID = " + MA_ID;
		
		Statement stmt = null;
		var result = new HashMap<String, String>();
		try {
			if(connection.isClosed()) {
				createConnection();
			}
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			connection.commit();
			rs.next();
			result.put("MA_Vorname", rs.getString(2));
			result.put("MA_Nachname", rs.getString(3));
			result.put("MA_Strasse", rs.getString(4));
			result.put("MA_PLZ", rs.getString(5));
			result.put("MA_Ort", rs.getString(6));
			result.put("MA_Abteilung", rs.getString(7));
			result.put("MA_Mail", rs.getString(8));
			result.put("MA_Urlaubstage", rs.getString(9));
			result.put("MA_Sollarbeitszeit", rs.getString(10));
			result.put("MA_aktiv", rs.getString(11));
			stmt.close();
		} catch (SQLException e) {
			System.err.println("Eintrag nicht gefunden");
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {}
		}
		return result;
	}
	
	public static void main(String[] args) {
		var ec = new EmployeeController();
		ec.deleteEmployee(3);
	}
	
}
