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
import exceptions.*;

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
		String query0 = "DELETE FROM login WHERE MA_ID = 1337";
		String query1 = "DELETE FROM mitarbeiter WHERE MA_ID = 1337";
		String query2 = "INSERT INTO mitarbeiter VALUES (1337, 'Johanna', 'Musterfrau', 'Schenkestr. 1', '10318', 'Berlin', 'Sekretariat', 'musterfraeulein@gmail.com', 30, '06:00:00', 'ja')";
		Statement stmt = null;
		try {
			createConnection();
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			stmt.addBatch(query0);
			stmt.addBatch(query1);
			stmt.addBatch(query2);
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
		String query0 = "DELETE FROM login WHERE MA_ID = 1337";
		String query1 = "DELETE FROM mitarbeiter WHERE MA_ID = 1337";
		Statement stmt = null;
		try {
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			stmt.addBatch(query0);
			stmt.addBatch(query1);
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
		String pw = "Zorua99";
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
		String pw = "Zorua99";
		assertTrue(CheckPassword.checkPW(1337, pw));
	}

	@Test
	@Order(3)
	void incorrectPasswordCheck() {
		String pw = "1337speak";
		assertFalse(CheckPassword.checkPW(1337, pw));
	}

	@Test
	@Order(4)
	void invalidUserTest() {
		String pw = "Zorua99";
		assertFalse(CheckPassword.checkPW(135, pw));
	}
	
	@Test
	@Order(5)
	void changePasswordTest() {
		String oldPw = "Zorua99";
		String newPw = "1337speak";
		try {
			if (!CheckPassword.changePW(1337, oldPw, newPw))
				fail("Password unchanged");
		} catch (FalsePasswordException fpe) {
			fail("Exception occured");
		} catch (NoChangeException nce) {
			fail("Exception occured");
		}
		assertTrue(CheckPassword.checkPW(1337, newPw));
		
	}
	
	@Test
	@Order(6)
	void changeWrongPasswordTest() {
		String oldPw = "Glaziola03";
		String newPw = "B1s4s4m";
		if (CheckPassword.checkPW(1337, oldPw))
			fail("WTF???");
		try {
			CheckPassword.changePW(1337, oldPw, newPw);
			fail("No exception occured");
		} catch (FalsePasswordException fpe) {
			assertTrue(true);
		} catch (NoChangeException nce) {
			fail("Wrong exception occured");
		}

	}
	
	@Test
	@Order(7)
	void changeSamePasswordTest() {
		String oldPw = "1337speak";
		String newPw = "1337speak";
		try {
			CheckPassword.changePW(1337, oldPw, newPw);
			fail("No Exception occured");
		} catch (FalsePasswordException fpe) {
			fail("Wrong Exception occured");
		} catch (NoChangeException nce) {
			assertTrue(true);
		}

	}

}
