package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import java.sql.*;
import database.*;
import java.util.ArrayList;
import java.util.HashMap;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DatabaseTest {

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
		String pass = "1234";
		System.out.println("Creating DBConnection");
		connection = DriverManager.getConnection(url, user, pass);
	}

	@BeforeAll
	private static void addTestMA() {
		String query0 = "DELETE FROM zeitkonto WHERE MA_ID = 134";
		String query1 = "DELETE FROM mitarbeiter WHERE MA_ID = 134";
		String query2 = "INSERT IGNORE INTO mitarbeiter VALUES (134, 'Erika', 'Musterfrau', 'G�rschstr. 32', '13187', 'Berlin', 'Buchhaltung', 'e.muster@gmail.com', 30, '08:00:00', 'ja')";
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
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
			}
		}
	}

	@AfterAll
	public static void deleteTestMA() {
		String query0 = "DELETE FROM zeitkonto WHERE MA_ID = 134";
		String query1 = "DELETE FROM mitarbeiter WHERE MA_ID = 134";
		Statement stmt = null;
		try {
			createConnection();
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			stmt.addBatch(query0);
			stmt.executeBatch();
			stmt.addBatch(query1);
			stmt.executeBatch();
			connection.commit();
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
	}

	@Test
	@Order(1)
	void addZeitkontoEintragTest() {
		String testDate = "2021-07-14";
		int id = 134;
		String begin = "08:00:00";
		String end = "18:00:00";
		String totalBreak = "01:00:00";
		String over = "01:00:00";
		String comment = "";
		AddWorkingTime addAz = new AddWorkingTime(testDate, id);
		try {
			addAz.addWorkingTime(begin, end, totalBreak, over, comment);
		} catch (BatchUpdateException bue) {
			fail("Exception occured");
		}

		ArrayList<String[]> expected = new ArrayList<String[]>();
		expected.add(new String[7]);
		expected.get(0)[0] = "2021-07-14";
		expected.get(0)[1] = "134";
		expected.get(0)[2] = "08:00:00";
		expected.get(0)[3] = "18:00:00";
		expected.get(0)[4] = "01:00:00";
		expected.get(0)[5] = "01:00:00";
		expected.get(0)[6] = "";
		String resultQuery = "SELECT * FROM zeitkonto WHERE MA_ID = 134";
		Statement stmt = null;
		ArrayList<String[]> result = new ArrayList<String[]>();
		try {
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(resultQuery);
			connection.commit();
			int i = 0;
			while (rs.next()) {
				result.add(new String[7]);
				for (int j = 0; j < 7; j++) {
					result.get(i)[j] = rs.getString(j+1);
				}
				i++;
			}
			stmt.close();
			connection.close();
		} catch (SQLException e) {
			fail("Exception occured");
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {

			}
		}
		for (int l = 0; l<result.size(); l++) {
			for (int k = 0; k<7; k++) {
				assertEquals(expected.get(l)[k], result.get(l)[k]);
			}
		}

	}

	@Test
	@Order(2)
	void updateZeitkontoEintragTest() {

		String testDate = "2021-07-14";
		int id = 134;
		String begin = "08:00:00";
		String end = "19:00:00";
		String totalBreak = "01:00:00";
		String over = "02:00:00";
		String comment = "neu";
		AddWorkingTime addAz = new AddWorkingTime(testDate, id);
		addAz.modifyWorkingTime(begin, end, totalBreak, over, comment);

		ArrayList<String[]> expected = new ArrayList<String[]>();
		expected.add(new String[7]);
		expected.get(0)[0] = "2021-07-14";
		expected.get(0)[1] = "134";
		expected.get(0)[2] = "08:00:00";
		expected.get(0)[3] = "19:00:00";
		expected.get(0)[4] = "01:00:00";
		expected.get(0)[5] = "02:00:00";
		expected.get(0)[6] = "neu";
		String resultQuery = "SELECT * FROM zeitkonto WHERE MA_ID = 134";
		Statement stmt = null;
		ArrayList<String[]> result = new ArrayList<String[]>();
		try {
			createConnection();
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(resultQuery);
			connection.commit();
			int i = 0;
			while (rs.next()) {
				result.add(new String[7]);
				for (int j = 0; j < 7; j++) {
					result.get(i)[j] = rs.getString(j+1);
				}
				i++;
			}
			stmt.close();
			connection.close();
		} catch (SQLException e) {
			fail("Exception occured");
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {

			}
		}
		for (int l = 0; l<result.size(); l++) {
			for (int k = 0; k<7; k++) {
				assertEquals(expected.get(l)[k], result.get(l)[k]);
			}
		}
	}

	@Test
	@Order(3)
	void BatchUpdateExceptionTest() {
		String testDate = "2021-07-14";
		int id = 134;
		String begin = "08:00:00";
		String end = "18:00:00";
		String totalBreak = "01:30:00";
		String over = "00:30:00";
		String comment = "";
		AddWorkingTime addAz = new AddWorkingTime(testDate, id);
		try {
			addAz.addWorkingTime(begin, end, totalBreak, over, comment);
			fail("Exception did not occur");
		} catch (BatchUpdateException bue) {
			assertTrue(true);
		}
	}

	@Test
	@Order(4)
	void getOvertimeSingleTest() {
		GetOvertime ot = new GetOvertime();
		String result = ot.getSum(134);

		String expected = "02:00:00";
		assertEquals(expected, result);
	}
	
	@Test
	@Order(5)
	void getNegativeOvertimeTest() {
		AddWorkingTime addAz = new AddWorkingTime("2021-07-15", 134);
		try {
			addAz.addWorkingTime("08:00:00", "12:00:00", "00:00:00", "-4:00:00", "halbtags");
		} catch (BatchUpdateException e) {
			fail("Exception occured");
		}
		
		GetOvertime ot = new GetOvertime();
		String result = ot.getSum(134);
		
		String expected = "-2:00:00";
		assertEquals(expected, result);
	}
	
	@Test
	@Order(6)
	void getWorkingTimeTest() {
		AddWorkingTime addAz = new AddWorkingTime("2021-07-15", 134);
		HashMap<String, String> result = addAz.getWorkingTime();
		
		HashMap<String, String> expected = new HashMap<String, String>();
		expected.put("Arbeitszeit_Beginn", "08:00:00");
		expected.put("Arbeitszeit_Ende", "12:00:00");
		expected.put("Pausengesamtzeit_Tag", "00:00:00");
		expected.put("Ueberstunden_Tag", "-4:00:00");
		expected.put("Kommentar", "halbtags");
		
		assertTrue(result.equals(expected));
	}

}
