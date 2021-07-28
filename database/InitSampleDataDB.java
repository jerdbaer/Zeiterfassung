package database;

/**
 * Program to initiate sample data for functionality demo
 * Initiates employees, employee login accounts, working time records.
 *  
 * @author Josephine Luksch
 * @author Simon Valiente
 * @author Julian Erxleben
 */

public class InitSampleDataDB {

	/**
	 * Main program starts initiation
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Create employees
		var newEmployeeData = new EmployeeController();
		
		newEmployeeData.createEmployee(103, "Finn", "Larsen", "Lila Straße 13", 
				"44444", "Berlin", "Design Team", "JoSi@sample.com", 28, 
				"07:30:00", "ja");
		
		newEmployeeData.createEmployee(105, "Sör", "Jul", "Blaue Straße 7", 
				"5555", "Berlin", "Backup rundrum", "SörJul@sample.com", 20, 
				"06:00:00", "ja");
		
		newEmployeeData.createEmployee(107, "Jo", "Si", "Rote Straße 10", 
				"11111", "Berlin", "Mädchen für alles", "JoSi@sample.com", 20, 
				"04:00:00", "ja");
		
		newEmployeeData.createEmployee(110, "Si", "Mon", "Gelbe Straße 15", 
				"22222", "Berlin", "DB Chef", "SiMon@sample.com", 25, 
				"06:00:00", "ja");
		
		newEmployeeData.createEmployee(115, "To", "Mmm", "Grüne Straße 6", 
				"33333", "Berlin", "Dev Chef", "ToMmm@sample.com", 15, 
				"04:00:00", "ja");
		
		newEmployeeData.createEmployee(1, "Luca", "Mustermensch", "Musterstraße 10", 
				"00000", "Berlin", "Team A", "LucaMustermensch@sample.com", 30, 
				"08:00:00", "ja");
		
		
		
		
		
		// init passwords for new employees
		CheckPassword.addAccount(1, "");
		CheckPassword.addAccount(103, "103");
		CheckPassword.addAccount(105, "105");
		CheckPassword.addAccount(107, "107");
		CheckPassword.addAccount(110, "110");
		
		// init working time records 
		
	}

}
