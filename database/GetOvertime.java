package database;

import java.sql.*;

/**
 * Program to select overtime data for specific time period
 *
 * @author Simon Valiente
 * @version 2.0
 */

 public class GetOvertime{

   private Connection connection;

   /**
    * Constructor
    * 
    * Calls a method to establish a connection to the database and defines its behavior after closing the program.
    *
    * @see createConnection()
    */
   public GetOvertime(){
     createConnection();
     Thread shutDownHook = new Thread() {
       public void run() {
         System.out.println("Running shutdown hook");
         if(connection == null)
           System.out.println("Connection to database already closed");
         try {
           if (connection != null && !connection.isClosed()){
             connection.close();
             if (connection.isClosed())
               System.out.println("Connection to database closed");
           }
         } catch (SQLException e){
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

   /**
	 * Establish connection to the database
	 */

   private void createConnection(){
     String url = "jdbc:mysql://localhost/zeiterfassung";
     String user = "root";
     String pass = "1234";
     try{
       System.out.println("Creating DBConnection");
       connection = DriverManager.getConnection(url, user, pass);
     } catch (SQLException e){
       System.err.println("Couldn't create DBConnection");
       System.exit(1);
     }
   }

   /**
    * Selects sum of overtime records for a selected employee to pass it as total overtime to UI
    * Remark: sum of overtime can be positive and negative
    *
    * @param MA_ID individual employee's id in int
    * @return sum of overtime records as String in hh:mm:ss / -hh:mm:ss for double digit hour values or 
    * -h:mm:ss for single digit hour values
    */

   public String getSum(int MA_ID){
     String query = "SELECT SEC_TO_TIME(SUM(TIME_TO_SEC(zeitkonto.Ueberstunden_Tag))) FROM zeitkonto "
      + "WHERE MA_ID = " + MA_ID;
      Statement stmt = null;
      String resultString = "";
      try{
        connection.setAutoCommit(false);
        stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        connection.commit();
        rs.next();
        resultString = rs.getString(1);
        stmt.close();

      } catch (SQLException e){
        e.printStackTrace();
      } finally {
        try{
          if (stmt != null)
            stmt.close();
        } catch (SQLException e){
        }
      }
      return resultString;
   }

   /**
    * Selects the time of working end of the database record on the day before the selected date for the new working 
    * time record for user input validation.
    * 
    * @param MA_ID individual employee's id in int
    * @param yesterday date of the day before the selected date for working time record
    * 
    * @return time of working end of the previous day as String String in hh:mm:ss. If there is no entry for the previous day, 
    * 00:00:00 will be returned to ensure that there will be no error in validation.
    */
   public String getWorkEndYesterday(int MA_ID, String yesterday){
     String query = "SELECT Arbeitszeit_Ende FROM zeitkonto "
       + "WHERE work_date = '" + yesterday + "' AND "
       + "MA_ID = " + MA_ID;
     Statement stmt = null;
     String resultString = "00:00:00";
     try {
       connection.setAutoCommit(false);
       stmt = connection.createStatement();
       ResultSet rs = stmt.executeQuery(query);
       connection.commit();
       rs.next();
       resultString = rs.getString(1);
       stmt.close();
     } catch (SQLException e){
       System.err.println("no WorkEndYesterday");
     } finally {
       try{
         if (stmt != null)
          stmt.close();
       } catch (SQLException e) {}
     }
     return resultString;
   }

   /**
    * Selects the time of working begin of the database record on the day after the selected date for the new working 
    * time record for user input validation.
    * 
    * @param MA_ID individual employee's id number in int
    * @param tomorrow date of the day after the selected date for working time record
    * 
    * @return time of working begin of the following day as String String in hh:mm:ss. If there is no entry for the following day, 
    * 23:59:59 will be returned to ensure that there will be mistake in validation.
    */
   public String getWorkBeginTomorrow(int MA_ID, String tomorrow){
     String query = "SELECT Arbeitszeit_Beginn FROM zeitkonto "
       + "WHERE work_date = '" + tomorrow + "' AND "
       + "MA_ID = " + MA_ID;
     Statement stmt = null;
     String resultString = "23:59:59";
     try {
       connection.setAutoCommit(false);
       stmt = connection.createStatement();
       ResultSet rs = stmt.executeQuery(query);
       connection.commit();
       rs.next();
       resultString = rs.getString(1);
       stmt.close();
     } catch (SQLException e){
       System.err.println("no WorkBeginTomorrow");
     } finally {
       try{
         if (stmt != null)
          stmt.close();
       } catch (SQLException e) {}
     }
     return resultString;
   }

 }
