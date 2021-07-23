package database;

import java.sql.*;

/**
 * Ein Programm zum Anlegen und Ändern von Zeitbuchungen.
 *
 * @author Simon Valiente
 * @version 1.2
 */

public class AddArbeitszeit{

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
  public AddArbeitszeit(String workDate, int MA_ID){
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
   * Legt in der Datenbank einen Eintrag für die Arbeitszeit an
   * Ein SQL-Befehl wird erstellt und an {@code commitQuery} übergeben
   *
   * @param beginTime ist der Beginn der Arbeitszeit im Format hh:mm:ss
   * @param endTime ist das Ende der Arbeitszeit im Format hh:mm:ss
   * @param totalBreak ist die Gesamtzeit der Pausen an dem Tag im Format hh:mm:ss
   * @param overtime ist die Anzahl der Überstunden, die an dem Tag geleistet wurden im Format hh:mm:ss
   * @param comment ist der Kommentar zum Arbeitszeiteintrag
   * @see commitQuery(String query, String method)
   */

  public boolean addArbeitszeit(String beginTime, String endTime, String totalBreak, String overtime, String comment){
    String query = "INSERT INTO zeitkonto VALUES ('" // Neuer Eintrag wird angelegt
        + workDate + "', "
        + MA_ID + ", '"
        + beginTime + "', '"
        + endTime + "', '"
        + totalBreak + "', '"
        + overtime + "', '"
        + comment + "')";

    return commitQuery(query, "add");
  }

  /**
   * Ändert an einem Tag für den/die angegebe:n Mitarbeiter:in die Zeiten
   * Achtung: Wenn der Eintrag nicht existiert, passiert nichts, nicht mal ein Fehler!
   * Ein SQL-Befehl wird erstellt und an {@code commitQuery} übergeben
   *
   * @param beginTime ist der Beginn der Arbeitszeit im Format hh:mm:ss
   * @param endTime ist das Ende der Arbeitszeit im Format hh:mm:ss
   * @param totalBreak ist die Gesamtzeit der Pausen an dem Tag im Format hh:mm:ss
   * @param overtime ist die Anzahl der Überstunden, die an dem Tag geleistet wurden im Format hh:mm:ss
   * @param comment ist der Kommentar zum Arbeitszeiteintrag
   * @see commitQuery(String query, String method)
   */

  public boolean modifyArbeitszeit(String beginTime, String endTime, String totalBreak, String overtime, String comment){
    String query = "UPDATE zeitkonto " // Eintrag bzw. Einträge werden überarbeitet
        + "SET Arbeitszeit_Beginn = '" + beginTime + "', "
        + "Arbeitszeit_Ende = '" + endTime + "', "
        + "Pausengesamtzeit_Tag = '" + totalBreak + "', "
        + "Ueberstunden_Tag = '" + overtime + "', "
        + "Kommentar = '" + comment + "' "
        + "WHERE work_date = '" + workDate + "' AND " // unter angegebenen Bedingungen
        + "MA_ID = " + MA_ID;

    return commitQuery(query, "modify");
  }

  /**
   * Übergibt die erhaltenen SQL-Befehle an die Datenbank, wo diese mit MySQL ausgeführt werden
   *
   * @param query ist der SQL-Befehl
   * @param method ist der Vorgang (hinzufügen oder ändern), der durchgeführt wird
   */

  public boolean commitQuery(String query, String method){
    Statement stmt = null;
    try{
      connection.setAutoCommit(false); // Das Statement
      stmt = connection.createStatement();
      stmt.addBatch(query);
      stmt.executeBatch();
      connection.commit();
      stmt.close();
      if (method == "add"){
        System.out.println("Eintrag erfolgreich angelegt");
      } else if (method == "modify"){
        System.out.println("Eintrag erfolgreich geändert");
      }
      return true;
    } catch (SQLException e){
      if (method == "add"){
        e.printStackTrace();
      } else if (method == "modify"){
        e.printStackTrace();
      } else {
        e.printStackTrace();
      }
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
   * Hauptprogramm. Tut nichts
   *
   * @param args Kommandozeilenparameter
   */

  public static void main(String[] args){
  }
}
