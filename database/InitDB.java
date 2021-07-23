package database;

import java.sql.*;

/**
 * Ein Programm zum Erstellen der Datenbank, die erstmal leer sein wird
 *
 * @author Simon Valiente
 * @author Julian Erxleben
 * @version 1.1
 */

public class InitDB{

  private Connection connection; // Erstellt ein Objekt der Klasse Connection

  /**
   * Stellt Verbindung zum MySQL-Treiber her.
   * Misslingt die Verbindung, ist womöglich der CLASSPATH falsch gesetzt
   */

  static {
    try {
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
   * Leerer Konstruktor, der die Methode zum Aufbau der Verbindung zur DB aufruft
   * und diese danach anlegt. Zudem legt er fest, was beim Schließen des Programms passiert
   *
   * @see createConnection()
   * @see createDBStructure()
   */

  public InitDB() {
    createConnection();
    createDBStructure();
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
   * Stellt die Verbindung zum Datenbankserver her
   */

  private void createConnection() {
    String url = "jdbc:mysql://localhost/?rewriteBatchedStatements=true";
    String user = "root";
    String pass = "";
    try {
      System.out.println("Creating DBConnection");
      connection = DriverManager.getConnection(url, user, pass);
    } catch (SQLException e) {
      System.err.println("Couldn't create DBConnection");
      System.exit(1);
    }
  }

  /**
   * Erstellt mittels SQL die benötigte Datenbank
   */

  private boolean createDBStructure() {
    String dbName = "Zeiterfassung";
    String query0 = "CREATE DATABASE IF NOT EXISTS " + dbName + ""; // Erstellt neue Datenbank, falls sie noch nicht existiert
    String query1 = "USE " + dbName + "";
    String query2 = "SET SQL_MODE='NO_AUTO_VALUE_ON_ZERO' ";
    String table0 = "CREATE TABLE IF NOT EXISTS Mitarbeiter (" // Erstellt neue Tabelle in der Datenbank
            + "MA_ID int NOT NULL, "
            + "MA_Vorname char(50) NOT NULL, "
            + "MA_Nachname char(50) NOT NULL, "
            + "MA_Strasse char(50) NOT NULL, "
            + "MA_PLZ char(5) NOT NULL, "
            + "MA_Ort char(50) NOT NULL, "
            + "MA_Abteilung char(50) NOT NULL, "
            + "MA_Mail char(50) NOT NULL, "
            + "MA_Urlaubstage int NOT NULL, "
            + "MA_Sollarbeitszeit time NOT NULL, "
            + "MA_aktiv enum('JA', 'NEIN') NOT NULL, "
            + "PRIMARY KEY (MA_ID))";
    String table1 = "CREATE TABLE IF NOT EXISTS Zeitkonto (" // Erstellt weitere neue Tabelle in der DB
            + "work_date date NOT NULL, "
            + "MA_ID int NOT NULL, "
            + "Arbeitszeit_Beginn time NOT NULL, "
            + "Arbeitszeit_Ende time NOT NULL, "
            + "Pausengesamtzeit_Tag time NOT NULL, "
            + "Ueberstunden_Tag time NOT NULL, "
            + "Kommentar char(50), "
            + "CONSTRAINT PK_Zeitkonto PRIMARY KEY (work_date,MA_ID), "
            + "FOREIGN KEY (MA_ID) REFERENCES Mitarbeiter(MA_ID))";
    String table2 = "CREATE TABLE IF NOT EXISTS login ("
            + "MA_ID int NOT NULL, "
            + "password int NOT NULL, " // Es wird der hashcode vom pw gespeichert
            + "PRIMARY KEY (MA_ID))";
            // Ich verknüpfe das doch nicht mit der MA-Tabelle, damit nicht der
            // MA existieren muss, um den Login zu erstellen

    Statement stmt = null;
    try{
      connection.setAutoCommit(false);
      stmt = connection.createStatement();
      stmt.addBatch(query0);
      stmt.addBatch(query1);
      stmt.addBatch(query2);
      stmt.addBatch(table0);
      stmt.addBatch(table1);
      stmt.addBatch(table2);
      stmt.executeBatch();
      connection.commit();
      stmt.close();
      connection.close();
      System.out.println("Database successfully created or just existing");
      return true;
    } catch (SQLException e){
      e.printStackTrace();
    } finally {
      try{
        if (stmt != null)
          stmt.close();
        if (connection != null)
          connection.close();
      } catch (SQLException e){
      }
    }
    return false;
  }

  /**
   * Hauptprogramm
   *
   * @param args Kommandozeilenparameter
   */

  public static void main(String[] args){
    new InitDB();
  }
}
