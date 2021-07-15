package database;

import java.sql.*;
import java.util.Calendar;

public class AddArbeitszeit{

  private Connection connection;

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

  public boolean addArbeitszeit(Date workDate, int MA_ID, Time beginTime,
                                  Time endTime, Time overtime){
    createConnection();
    String query = "INSERT INTO zeitkonto VALUES ('"
        + workDate + "', "
        + MA_ID + ", '"
        + beginTime + "', '"
        + endTime + "', '"
        + overtime + "')";

    Statement stmt = null;
    try{
      connection.setAutoCommit(false);
      stmt = connection.createStatement();
      stmt.addBatch(query);
      stmt.executeBatch();
      connection.commit();
      stmt.close();
      connection.close();
      System.out.println("Eintrag erfolgreich angelegt");
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

  public static void main(String[] args){
    long a = Long.parseLong("1626213600000");
    Date date = new Date(a);
    int id = 134;
    Time begin = new Time(28800000-3600000);
    Time end = new Time(64800000-3600000);
    Time over = new Time(0);
    AddArbeitszeit az = new AddArbeitszeit();
    az.addArbeitszeit(date, id, begin, end, over);
  }
}
