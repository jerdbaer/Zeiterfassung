package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import java.sql.*;
import database.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginTest {
	
	private static Connection connection;

	static {
	     try{
	       Class<?> c = Class.forName("com.mysql.cj.jdbc.Driver");
	       if (c != null){
	         System.out.println("JDBC-Treiber geladen");
	       }
	     } catch (ClassNotFoundException e){
	       System.err.println("Fehler beim Laden des JDBC-Treibers");
	       System.exit(1);
	     }
	   }

	private static void createConnection() throws SQLException {
		String url = "jdbc:mysql://localhost/zeiterfassung";
		String user = "root";
		String pass = "";
		System.out.println("Creating DBConnection");
		connection = DriverManager.getConnection(url, user, pass);
	}

	@BeforeAll
	static void setUpBeforeClass() {
		String query = "DELETE FROM login WHERE MA_ID = 1337";
		Statement stmt = null;
		try {
			createConnection();
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			stmt.addBatch(query);
			stmt.executeBatch();
			connection.commit();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
			}
		}
	}
	
	@AfterAll
	static void cleanUp() {
		String query = "DELETE FROM login WHERE MA_ID = 1337";
		Statement stmt = null;
		try {
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			stmt.addBatch(query);
			stmt.executeBatch();
			connection.commit();
			stmt.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
			}
		}
	}

	@Test
	@Order(1)
	void addUserTest() {
		int pw = "Zorua99".hashCode();
		CheckPassword.addAccount(1337, pw);
		
		int[] expected = new int[2];
		expected[0] = 1337;
		expected[1] = "Zorua99".hashCode();
		
		int[] result = new int[2];
		String resultQuery = "SELECT * FROM login WHERE MA_ID = 1337";
		Statement stmt = null;
		try {
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(resultQuery);
			rs.next();
			result[0] = rs.getInt("MA_ID");
			result[1] = rs.getInt("password");
			assertEquals(expected[0], result[0]);
			assertEquals(expected[1], result[1]);
			stmt.close();
		} catch (SQLException e) {
			fail("Exception occured");
		} finally {
			try {
				if(stmt != null)
					stmt.close();
			} catch (SQLException e){
			}
		}
	}
	
	@Test
	@Order(2)
	void correctPasswordCheck() {
		int pw = "Zorua99".hashCode();
		assertTrue(CheckPassword.checkPW(1337, pw));
	}
	
	@Test
	@Order(3)
	void incorrectPasswordCheck() {
		int pw = "1337speak".hashCode();
		assertFalse(CheckPassword.checkPW(1337, pw));
	}
	
	@Test
	@Order(4)
	void invalidUserTest() {
		int pw = "Zorua99".hashCode();
		assertFalse(CheckPassword.checkPW(135, pw));
	}

}
