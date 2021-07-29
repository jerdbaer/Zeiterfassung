package database;

import static org.junit.jupiter.api.Assertions.fail;

import java.sql.BatchUpdateException;

/**
 * Program to initiate sample data for functionality demo Initiates employees,
 * employee login accounts, working time records.
 *
 * @author Josephine Luksch
 * @author Simon Valiente
 * @author Julian Erxleben
 */

public class InitSampleDataInDB {

	/**
	 * Main program starts initiation
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		// Create employees
		var newEmployeeData = new EmployeeController();

		newEmployeeData.createEmployee(103, "Finn", "Larsen", "Lila Straße 13", "44444", "Berlin", "Design Team",
				"JoSi@sample.com", 28, "07:30:00", "ja");

		newEmployeeData.createEmployee(105, "Sör", "Jul", "Blaue Straße 7", "5555", "Berlin", "Backup rundrum",
				"SörJul@sample.com", 20, "06:00:00", "ja");

		newEmployeeData.createEmployee(107, "Jo", "Si", "Rote Straße 10", "11111", "Berlin", "Mädchen für alles",
				"JoSi@sample.com", 20, "04:00:00", "ja");

		newEmployeeData.createEmployee(110, "Si", "Mon", "Gelbe Straße 15", "22222", "Berlin", "DB Chef",
				"SiMon@sample.com", 25, "06:00:00", "ja");

		newEmployeeData.createEmployee(115, "To", "Mmm", "Grüne Straße 6", "33333", "Berlin", "Dev Chef",
				"ToMmm@sample.com", 15, "04:00:00", "ja");

		newEmployeeData.createEmployee(1, "Luca", "Mustermensch", "Musterstraße 10", "00000", "Berlin", "Team A",
				"LucaMustermensch@sample.com", 30, "08:00:00", "ja");

		// init passwords for new employees
		CheckPassword.addAccount(1, "");
		CheckPassword.addAccount(103, "103");
		CheckPassword.addAccount(105, "105");
		CheckPassword.addAccount(107, "107");
		CheckPassword.addAccount(110, "110");

		// init working time records
		// ----- 1 -----
		AddWorkingTime newEntry1_1 = new AddWorkingTime("2021-07-12", 1);
		AddWorkingTime newEntry1_2 = new AddWorkingTime("2021-07-13", 1);
		AddWorkingTime newEntry1_3 = new AddWorkingTime("2021-07-14", 1);
		AddWorkingTime newEntry1_4 = new AddWorkingTime("2021-07-15", 1);
		AddWorkingTime newEntry1_5 = new AddWorkingTime("2021-07-16", 1);

		try {
			newEntry1_1.addWorkingTime("08:00:00", "16:30:00", "0:30:00", "0:00:00", "");
			newEntry1_2.addWorkingTime("08:00:00", "16:30:00", "0:30:00", "0:00:00", "");
			newEntry1_3.addWorkingTime("08:00:00", "16:30:00", "0:30:00", "0:00:00", "");
			newEntry1_4.addWorkingTime("08:00:00", "16:30:00", "0:30:00", "0:00:00", "");
			newEntry1_5.addWorkingTime("08:00:00", "16:30:00", "0:30:00", "0:00:00", "");
		} catch (BatchUpdateException bue) {
			fail("Exception occured");
		}

		// ----- 103 -----
		AddWorkingTime newEntry103_1 = new AddWorkingTime("2021-07-19", 103);
		AddWorkingTime newEntry103_2 = new AddWorkingTime("2021-07-20", 103);
		AddWorkingTime newEntry103_3 = new AddWorkingTime("2021-07-21", 103);
		AddWorkingTime newEntry103_4 = new AddWorkingTime("2021-07-22", 103);
		AddWorkingTime newEntry103_5 = new AddWorkingTime("2021-07-23", 103);

		try {
			newEntry103_1.addWorkingTime("07:00:00", "16:00:00", "01:00:00", "00:30:00", "");
			newEntry103_2.addWorkingTime("07:00:00", "17:30:00", "02:00:00", "01:00:00", "");
			newEntry103_3.addWorkingTime("07:00:00", "16:00:00", "01:00:00", "00:30:00", "");
			newEntry103_4.addWorkingTime("07:00:00", "17:30:00", "02:00:00", "01:00:00", "");
			newEntry103_5.addWorkingTime("07:00:00", "16:00:00", "01:00:00", "00:30:00", "");
		} catch (BatchUpdateException bue) {
			fail("Exception occured");
		}
		// ----- 107 -----
		AddWorkingTime newEntry107_1 = new AddWorkingTime("2021-07-05", 107);
		AddWorkingTime newEntry107_2 = new AddWorkingTime("2021-07-06", 107);
		AddWorkingTime newEntry107_3 = new AddWorkingTime("2021-07-07", 107);
		AddWorkingTime newEntry107_4 = new AddWorkingTime("2021-07-08", 107);
		AddWorkingTime newEntry107_5 = new AddWorkingTime("2021-07-09", 107);

		try {
			newEntry107_1.addWorkingTime("08:00:00", "13:30:00", "00:30:00", "01:00:00", "");
			newEntry107_2.addWorkingTime("08:00:00", "12:00:00", "00:30:00", "-0:30:00", "");
			newEntry107_3.addWorkingTime("08:00:00", "14:30:00", "00:30:00", "02:00:00", "");
			newEntry107_4.addWorkingTime("08:00:00", "12:00:00", "00:30:00", "-0:30:00", "");
			newEntry107_5.addWorkingTime("08:00:00", "15:30:00", "00:30:00", "03:00:00", "");
		} catch (BatchUpdateException bue) {
			fail("Exception occured");
		}

		// ----- 110 -----
		AddWorkingTime newEntry110_1 = new AddWorkingTime("2021-07-12", 110);
		AddWorkingTime newEntry110_2 = new AddWorkingTime("2021-07-13", 110);
		AddWorkingTime newEntry110_3 = new AddWorkingTime("2021-07-14", 110);
		AddWorkingTime newEntry110_4 = new AddWorkingTime("2021-07-15", 110);
		AddWorkingTime newEntry110_5 = new AddWorkingTime("2021-07-16", 110);

		try {
			newEntry110_1.addWorkingTime("15:00:00", "21:30:00", "00:30:00", "00:00:00", "Stromausfall");
			newEntry110_2.addWorkingTime("11:00:00", "19:00:00", "00:30:00", "01:30:00", "");
			newEntry110_3.addWorkingTime("06:00:00", "19:30:00", "03:30:00", "04:00:00", "");
			newEntry110_4.addWorkingTime("08:00:00", "12:00:00", "00:00:00", "-2:00:00", "");
			newEntry110_5.addWorkingTime("08:00:00", "15:30:00", "00:30:00", "01:00:00", "");
		} catch (BatchUpdateException bue) {
			fail("Exception occured");
		}

		// ----- 115 -----
		AddWorkingTime newEntry115_1 = new AddWorkingTime("2021-07-05", 115);
		AddWorkingTime newEntry115_2 = new AddWorkingTime("2021-07-06", 115);
		AddWorkingTime newEntry115_3 = new AddWorkingTime("2021-07-07", 115);
		AddWorkingTime newEntry115_4 = new AddWorkingTime("2021-07-08", 115);
		AddWorkingTime newEntry115_5 = new AddWorkingTime("2021-07-09", 115);

		try {
			newEntry115_1.addWorkingTime("08:00:00", "18:00:00", "01:00:00", "01:00:00", "Home Office");
			newEntry115_2.addWorkingTime("08:00:00", "16:00:00", "00:30:00", "-0:30:00", "Amt");
			newEntry115_3.addWorkingTime("08:00:00", "16:30:00", "00:30:00", "00:00:00", "Amt");
			newEntry115_4.addWorkingTime("08:00:00", "16:00:00", "01:00:00", "-0:30:00", "Amt");
			newEntry115_5.addWorkingTime("08:00:00", "16:00:00", "00:30:00", "03:00:00", "Home Office");
		} catch (BatchUpdateException bue) {
			fail("Exception occured");
		}

	}

}