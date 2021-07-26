package database;

import java.sql.*;
import exceptions.FalsePasswordException;
import exceptions.NoChangeException;

/**
 * Program to validate password input
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
    * Establish connection to MySQL driver
	* Remark: if connection fails, check if CLASSPATH variable is set correctly.
	*/

  private static void createConnection(){
    String url = "jdbc:mysql://localhost/zeiterfassung";
    String user = "root";
    String pass = "1234";
    try {
      System.out.println("Creating DBConnection");
      connection = DriverManager.getConnection(url, user, pass);
    } catch (SQLException e) {
      System.err.println("Couldn't create DBConnection");
      System.exit(1);
    }
  }

  /**
   * Validates, if the input password matches the stored password in the database for specific employee's account
   *
   * @param MA_ID individual employee's id in int, for which password should be validated 
   * @param pw password input which needs validation
   *
   * @return boolean, if input password matches the stored password value 
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
   * Adds a new login account for a specific employee including employee's id and password
   * Password is not stored directly, but as hashcode.
   *
   * @param MA_ID individual employee's id for which the new login account should be created 
   * @param pw password for new employee's account
   * @return feedback, if account was created successfully 
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
   * Overwrites the stored password for an existing employee's account in case the old password was provided correctly 
   * by the user's input
   *
   * @param MA_ID employee' account for which the password should be overwritten 
   * @param oldPw old password
   * @param newPw new password
   * 
   * @return feedback, if the password was updated (overwritten) successfully
   * 
   * @throws FalsePasswordException when {@code oldPw} is false 
   * @throws NoChangeException when {@code oldPw == newPw}
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
