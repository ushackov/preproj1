package dao.utils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {

  public static Connection getPostgresConnection() {
    try {
      DriverManager.registerDriver((Driver) Class
          .forName("org.postgresql.Driver").newInstance());

      String url = "jdbc:postgresql://localhost:5432/postgres";
      String pass = "postgres";
      String login = "postgres";

      System.out.println("URL: " + url + "\n");
      return DriverManager.getConnection(url, login, pass);
    } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void printConnectInfo(Connection connection) {
    try {
      System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
      System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
      System.out.println("Driver: " + connection.getMetaData().getDriverName());
      System.out.println("Autocommit: " + connection.getAutoCommit());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
