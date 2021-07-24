package database;

import java.sql.*;
import java.util.HashMap;

/**
 * Ein Programm zum Anlegen und Ändern von Zeitbuchungen.
 *
 * @author Simon Valiente
 * @version 2.0
 */

public class AddWorkingTime{

  private Connection connection; // Erstellt ein Objekt der Klasse Connection
  private String workDate;
  private int MA_ID;

  /**
   * Konstruktor, ruft die Methode zum Aufbau der Verbindung zur DB auf
   * und legt bereits fest, was beim Schließen des Programms passiert
   *
   * @param workDate ist das Datum des Arbeitstages im Format yyyy-mm-dd
   * @param MA_ID ist die Personalnummer des/der Mitarbeiter:in
   *
   * @see createConnection()
   */
  public AddWorkingTime(String workDate, int MA_ID){
    this.workDate = workDate;
    this.MA_ID = MA_ID;
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
   * Legt in der Datenbank einen Eintrag für die Arbeitszeit an
   * Ein SQL-Befehl wird erstellt und an die Datenbank übergeben, wo dieser ausgeführt wird.
   *
   * @param beginTime ist der Beginn der Arbeitszeit im Format hh:mm:ss
   * @param endTime ist das Ende der Arbeitszeit im Format hh:mm:ss
   * @param totalBreak ist die Gesamtzeit der Pausen an dem Tag im Format hh:mm:ss
   * @param overtime ist die Anzahl der Überstunden, die an dem Tag geleistet wurden im Format hh:mm:ss
   * @param comment ist der Kommentar zum Arbeitszeiteintrag
   * @throws BatchUpdateException wenn bereits ein Eintrag für den MA und das Datum existiert (wird extern abgefangen)
   * @return Rückmeldung, ob der Eintrag erfolgreich angelegt wurde
   */

  public boolean addWorkingTime(String beginTime, String endTime, String totalBreak, String overtime, String comment) throws BatchUpdateException{
    String query = "INSERT INTO zeitkonto VALUES ('" // Neuer Eintrag wird angelegt
        + workDate + "', "
        + MA_ID + ", '"
        + beginTime + "', '"
        + endTime + "', '"
        + totalBreak + "', '"
        + overtime + "', '"
        + comment + "')";

        Statement stmt = null;
        try{
          connection.setAutoCommit(false); // Das Statement
          stmt = connection.createStatement();
          stmt.addBatch(query);
          stmt.executeBatch();
          connection.commit();
          stmt.close();
          System.out.println("Eintrag erfolgreich angelegt");
          return true;
        } catch (BatchUpdateException e){
          throw new BatchUpdateException();
        } catch (SQLException sqle){
          sqle.printStackTrace();
        } finally {
          try{
            if (stmt != null)
              stmt.close();
          } catch (SQLException e){
          }
        }
        return false;
  }

  /**
   * Ändert an einem Tag für den/die angegebe:n Mitarbeiter:in die Zeiten
   * Achtung: Wenn der Eintrag nicht existiert, passiert nichts, nicht mal ein Fehler!
   * Ein SQL-Befehl wird erstellt und an die Datenbank übergeben, wo dieser ausgeführt wird.
   *
   * @param beginTime ist der Beginn der Arbeitszeit im Format hh:mm:ss
   * @param endTime ist das Ende der Arbeitszeit im Format hh:mm:ss
   * @param totalBreak ist die Gesamtzeit der Pausen an dem Tag im Format hh:mm:ss
   * @param overtime ist die Anzahl der Überstunden, die an dem Tag geleistet wurden im Format hh:mm:ss
   * @param comment ist der Kommentar zum Arbeitszeiteintrag
   * @return Rückmeldung, ob der Eintrag erfolgreich geändert wurde
   */

  public boolean modifyWorkingTime(String beginTime, String endTime, String totalBreak, String overtime, String comment){
    String query = "UPDATE zeitkonto " // Eintrag bzw. Einträge werden überarbeitet
        + "SET Arbeitszeit_Beginn = '" + beginTime + "', "
        + "Arbeitszeit_Ende = '" + endTime + "', "
        + "Pausengesamtzeit_Tag = '" + totalBreak + "', "
        + "Ueberstunden_Tag = '" + overtime + "', "
        + "Kommentar = '" + comment + "' "
        + "WHERE work_date = '" + workDate + "' AND " // unter angegebenen Bedingungen
        + "MA_ID = " + MA_ID;

      Statement stmt = null;
      try{
        connection.setAutoCommit(false); // Das Statement
        stmt = connection.createStatement();
        stmt.addBatch(query);
        stmt.executeBatch();
        connection.commit();
        stmt.close();
        System.out.println("Eintrag erfolgreich geändert");
        return true;
      } catch (SQLException sqle){
        sqle.printStackTrace();
      } finally {
        try{
          if (stmt != null)
            stmt.close();
        } catch (SQLException e){
        }
      }
      return false;
  }

  public HashMap<String, String> getWorkingTime(){
    String query = "SELECT Arbeitszeit_Beginn, Arbeitszeit_Ende, Pausengesamtzeit_Tag, Ueberstunden_Tag, Kommentar "
     + "FROM zeitkonto WHERE "
     + "work_date  = '" + workDate + "' AND "
     + "MA_ID = " + MA_ID;
     Statement stmt = null;
     HashMap<String, String> result = new HashMap<String, String>();

     try{
       connection.setAutoCommit(false);
       stmt = connection.createStatement();
       ResultSet rs = stmt.executeQuery(query);
       connection.commit();
       rs.next();
       result.put("Arbeitszeit_Beginn", rs.getString(1));
       result.put("Arbeitszeit_Ende", rs.getString(2));
       result.put("Pausengesamtzeit_Tag", rs.getString(3));
       result.put("Ueberstunden_Tag", rs.getString(4));
       result.put("Kommentar", rs.getString(5));
       stmt.close();
     } catch (SQLException e){
       e.printStackTrace();
     } finally {
       try {
         if (stmt != null)
          stmt.close();
       } catch (SQLException e){}
     }
     return result;
  }

  /**
   * Hauptprogramm. Tut nichts
   *
   * @param args Kommandozeilenparameter
   */

  public static void main(String[] args){
    AddWorkingTime addAz = new AddWorkingTime("2021-02-04", 134);
    try{
        addAz.addWorkingTime("08:00:00", "16:30:00", "00:30:00", "00:00:00", "");
        System.out.println("Eintrag 1 erfolgreich");
        addAz.addWorkingTime("08:00:00", "17:30:00", "00:30:00", "01:00:00", "");
        System.out.println("Something went wrong...");
    } catch (BatchUpdateException e){
      System.out.println("Einmal ist gut, zweimal ist schlecht");
    }

  }
}
