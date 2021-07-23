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
		String pass = "";
		System.out.println("Creating DBConnection");
		connection = DriverManager.getConnection(url, user, pass);
	}
	
	@BeforeAll
	private static void addTestMA() {
		String query0 = "DELETE FROM zeitkonto WHERE MA_ID = 134";
		String query1 = "DELETE FROM zeitkonto WHERE MA_ID = 138";
		String query2 = "DELETE FROM mitarbeiter WHERE MA_ID = 134";
		String query3 = "DELETE FROM mitarbeiter WHERE MA_ID = 138";
		String query4 = "INSERT IGNORE INTO mitarbeiter VALUES (134, 'Erika', 'Musterfrau', 'G�rschstr. 32', '13187', 'Berlin', 'Buchhaltung', 'e.muster@gmail.com', 30, '08:00:00', 'ja')";
		String query5 = "INSERT IGNORE INTO mitarbeiter VALUES (138, 'Matthias', 'Musterfrau', 'G�rschstr. 32', '13187', 'Berlin', 'Buchhaltung', 'mat.muster@gmail.com', 30, '08:00:00', 'ja')";
		Statement stmt = null;
		try {
			createConnection();
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			stmt.addBatch(query0);
			stmt.addBatch(query1);
			stmt.addBatch(query2);
			stmt.addBatch(query3);
			stmt.addBatch(query4);
			stmt.addBatch(query5);
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
		String query1 = "DELETE FROM zeitkonto WHERE MA_ID = 138";
		String query2 = "DELETE FROM mitarbeiter WHERE MA_ID = 134";
		String query3 = "DELETE FROM mitarbeiter WHERE MA_ID = 138";
		Statement stmt = null;
		try {
			createConnection();
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			stmt.addBatch(query0);
			stmt.addBatch(query1);
			stmt.addBatch(query2);
			stmt.addBatch(query3);
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
		AddArbeitszeit addAz = new AddArbeitszeit(testDate, id);
		addAz.addArbeitszeit(begin, end, totalBreak, over, comment);

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
		AddArbeitszeit addAz = new AddArbeitszeit(testDate, id);
		addAz.modifyArbeitszeit(begin, end, totalBreak, over, comment);

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
	void getOvertimeSingleTest() {
		GetOvertime ot = new GetOvertime();
		ArrayList<String[]> result = ot.getSum(134);

		ArrayList<String[]> expected = new ArrayList<String[]>();
		expected.add(new String[2]);
		expected.get(0)[0] = "134";
		expected.get(0)[1] = "02:00:00";

		for (int i = 0; i < result.size(); i++) {
			for (int j = 0; j < 2; j++) {
				assertEquals(expected.get(i)[j], result.get(i)[j]);
			}
		}
	}

	@Test
	@Order(4)
	void getOvertimeAbteilungTest() {
		String testDate = "2021-07-14";
		int id = 138;
		String begin = "08:00:00";
		String end = "18:00:00";
		String totalBreak = "01:00:00";
		String over = "01:00:00";
		String comment = "";
		// I may need to resolve the issue time zone if we keep this format
		AddArbeitszeit addAz = new AddArbeitszeit(testDate, id);
		addAz.addArbeitszeit(begin, end, totalBreak, over, comment);

		GetOvertime ot = new GetOvertime();
		ArrayList<String[]> result = ot.getSum("Buchhaltung");

		ArrayList<String[]> expected = new ArrayList<String[]>();
		expected.add(new String[2]);
		expected.get(0)[0] = "134";
		expected.get(0)[1] = "02:00:00";
		expected.add(new String[2]);
		expected.get(1)[0] = "138";
		expected.get(1)[1] = "01:00:00";

		for (int i = 0; i < result.size(); i++) {
			for (int j = 0; j < 2; j++) {
				assertEquals(expected.get(i)[j], result.get(i)[j]);
			}
		}
	}


}
