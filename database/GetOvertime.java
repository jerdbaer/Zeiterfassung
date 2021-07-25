package database;

import java.sql.*;

/**
 * Ein Programm zum Erhalt der Überstunden.
 *
 * @author Simon Valiente
 * @version 2.0
 */

 public class GetOvertime{

   private Connection connection;

   /**
    * Konstruktor, ruft die Methode zum Aufbau der Verbindung zur DB auf
    * und legt bereits fest, was beim Schließen des Programms passiert
    *
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
    * Stellt Verbindung zum MySQL-Treiber her.
    * Misslingt die Verbindung, ist womöglich der CLASSPATH falsch gesetzt
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
    * Stellt die Verbindung zur Datenbank her
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
    * Zieht die Summe der Überstunden von einem Mitarbeiter.
    *
    * @param MA_ID ist die ID des/der Mitarbeiter:in
    * @return Summe der Überstunden als String im Format hh:mm:ss (negativ einstellig: -h:mm:ss, negativ zweistellig -hh:mm:ss)
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

   public String getWorkBeginTomorrow(int MA_ID, String tomorrow){
     String query = "SELECT Arbeitszeit_Ende FROM zeitkonto "
       + "WHERE work_date = '" + tomorrow + "' AND "
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
       System.err.println("no WorkBeginTomorrow");
     } finally {
       try{
         if (stmt != null)
          stmt.close();
       } catch (SQLException e) {}
     }
     return resultString;
   }

   public static void main(String[] args){

   }

 }
