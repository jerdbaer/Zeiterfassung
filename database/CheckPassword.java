package database;

import java.sql.*;

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

  public static boolean checkPW(int MA_ID, int pwHash){
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

  public static boolean addAccount(int MA_ID, int pwHash){
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
}
