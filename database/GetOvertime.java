package database;

import java.sql.*;
import java.util.ArrayList;

/**
 * Ein Programm zum Erhalt der Überstunden.
 *
 * @author Simon Valiente
 * @version 1.0
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
     String pass = "";
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
    */

   public ArrayList<String[]> getSum(int MA_ID){
     String query = "SELECT mitarbeiter.MA_ID, SEC_TO_TIME(SUM(TIME_TO_SEC(zeitkonto.Ueberstunden_Tag))) FROM mitarbeiter "
      + "LEFT JOIN zeitkonto ON mitarbeiter.MA_ID = zeitkonto.MA_ID "
      + "WHERE mitarbeiter.MA_ID = " + MA_ID
      + " GROUP BY mitarbeiter.MA_ID";
      Statement stmt = null;
      ArrayList<String[]> result = new ArrayList<String[]>();
      try{
        connection.setAutoCommit(false);
        stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        connection.commit();
        int i = 0;
        while (rs.next()){
          result.add(new String[2]);
          result.get(i)[0] = rs.getString(1);
          result.get(i++)[1] = rs.getString(2);
        }
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
      return result;
   }

   /**
    * Zieht die Summe der Überstunden von einer Abteilung, gruppiert nach Mitarbeiter.
    *
    * @param Abteilung ist die ID des/der Mitarbeiter:in
    */

   public ArrayList<String[]> getSum(String abteilung){
     String query = "SELECT mitarbeiter.MA_ID, SEC_TO_TIME(SUM(TIME_TO_SEC(zeitkonto.Ueberstunden_Tag))) FROM mitarbeiter "
      + "LEFT JOIN zeitkonto ON mitarbeiter.MA_ID = zeitkonto.MA_ID "
      + "WHERE mitarbeiter.MA_Abteilung = '" + abteilung + "'"
      + " GROUP BY mitarbeiter.MA_ID";
      Statement stmt = null;
      ArrayList<String[]> result = new ArrayList<String[]>();
      try{
        connection.setAutoCommit(false);
        stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        connection.commit();
        int i = 0;
        while (rs.next()){
          result.add(new String[2]);
          result.get(i)[0] = rs.getString(1);
          result.get(i++)[1] = rs.getString(2);
        }
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
      return result;
   }

   public static void main(String[] args){

   }

 }
