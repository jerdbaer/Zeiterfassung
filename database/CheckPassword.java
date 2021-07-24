package database;

import java.sql.*;
import exceptions.FalsePasswordException;
import exceptions.NoChangeException;

/**
 * Ein Programm zum überprüfen des Passworts
 * Die Klasse ist möglicherweise nur ein Zwischenspeicher
 *
 * @author Simon Valiente
 * @version 1.0
 */

public class CheckPassword{

  private static Connection connection;

  static {
    try {
      Class<?> c = Class.forName("com.mysql.cj.jdbc.Driver");
      if (c != null) {
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

  private static void createConnection(){
    String url = "jdbc:mysql://localhost/zeiterfassung";
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
   * Überprüft das Passwort zu einer bestimmten MA_ID
   *
   * @param MA_ID ist die ID des Mitarbeiters, dessen Passwort überprüft werden soll
   * @param pw ist das Passwort, das überprüft werden soll
   *
   * @return boolean, ob das angegebene Passwort mit dem gespeicherten Passwort übereinstimmt
   */

  public static boolean checkPW(int MA_ID, String pw){
    int pwHash = pw.hashCode();
    String query = "SELECT password FROM login WHERE MA_ID = " + MA_ID;
    Statement stmt = null;
    try {
      createConnection();
      connection.setAutoCommit(false);
      stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      connection.commit();
      rs.next();
      int result = rs.getInt("password");
      stmt.close();
      connection.close();
      return result == pwHash;
    } catch (SQLException e) {
      System.err.println("User liegt nicht vor");
    } finally {
      try {
        if (stmt != null)
          stmt.close();
        if (connection != null)
          connection.close();
      } catch (SQLException e) {

      }
    }
    return false;
  }

  /**
   * Fügt einen neuen Account mit MA_ID und Passwort hinzu
   * Das Passwort wird hierbei nicht direkt, sondern als Hashcode gespeichert.
   *
   * @param MA_ID die ID des Mitarbeiters, dessen Konto angelegt werden soll
   * @param pw das Passwort für den Account
   * @return Angabe, ob der Eintrag erfolgreich angelegt wurde
   */

  public static boolean addAccount(int MA_ID, String pw){
    int pwHash = pw.hashCode();
    String query = "INSERT INTO login VALUES (" + MA_ID + ", " + pwHash + ")";
    Statement stmt = null;
    try {
      createConnection();
      connection.setAutoCommit(false);
      stmt = connection.createStatement();
      stmt.addBatch(query);
      stmt.executeBatch();
      connection.commit();
      stmt.close();
      connection.close();
      return true;
    } catch (BatchUpdateException e) {
      System.err.println("Eintrag existiert bereits");
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (stmt != null)
          stmt.close();
        if (connection != null)
          connection.close();
      } catch (SQLException e) {

      }
    }
    return false;
  }

  /**
   * Ändert das Passwort für den Account MA_ID, wenn das alte Passwort korrekt ist
   *
   * @param MA_ID ist der Account, für den das Passwort geändert werden soll
   * @param oldPw ist das alte Passwort
   * @param newPw ist das neue Passwort
   * @return Angabe, ob das Passwort erfoglreich geändert wurde
   * @throws FalsePasswordException wenn {@code oldPw} falsch ist
   * @throws NoChangeException wenn {@code oldPw == newPw}
   */

  public static boolean changePW(int MA_ID, String oldPw, String newPw) throws FalsePasswordException, NoChangeException {
    int oldPwHash = oldPw.hashCode();
    int newPwHash = newPw.hashCode();
    boolean checkOld = checkPW(MA_ID, oldPw);
    if(checkOld && (oldPwHash != newPwHash)){
      String query = "UPDATE login SET password = " + newPwHash + " WHERE MA_ID = " + MA_ID;
      Statement stmt = null;
      try{
        createConnection();
        connection.setAutoCommit(false);
        stmt = connection.createStatement();
        stmt.addBatch(query);
        stmt.executeBatch();
        connection.commit();
        stmt.close();
        connection.close();
        return true;
      } catch (SQLException e){
        e.printStackTrace();
      } finally {
        try{
          if (stmt != null)
            stmt.close();
          if (connection != null)
            connection.close();
        } catch (SQLException e) {}
      }
    } else if (!checkOld){
      throw new FalsePasswordException();
    } else {
      throw new NoChangeException();
    }
    return false;

  }
}
