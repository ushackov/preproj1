package dao;

import dao.executor.Executor;
import dao.utils.ConnectionUtils;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcDAO implements DAO {

  private final Executor executor;
  Connection connection;
  private final String sql = "SELECT * FROM example WHERE lastName=?";
  private final String sql2 = "DELETE FROM example WHERE lastName=?";


  public JdbcDAO() {
    this.connection = ConnectionUtils.getPostgresConnection();
    this.executor = new Executor();
    ConnectionUtils.printConnectInfo(connection);
  }

  @Override
  public User get(String lastName) {
    try {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, lastName);
      return executor.execQuery(statement, result -> {
        if (!result.next()) {
          return null;
        }
        return new User(result.getLong(1), result.getString(2), result.getString(3)
            , result.getByte(4));
      });
    } catch (SQLException e) {
      System.out.println("Ошибка при поиске юзера");
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public boolean removeUserByLastName(String lastName) {
    try {
      PreparedStatement statement = connection.prepareStatement(sql2);
      statement.setString(1, lastName);
      return executor.execUpdate(statement);
    } catch (SQLException e) {
      System.out.println("Юзер не удален");
    }
    return false;
  }

  public boolean insertUser(String name, String lastName, byte age) {
    try {
      connection.setAutoCommit(false);
      createTable();
      String updSql = "INSERT INTO example (id, name, lastName, age) VALUES (DEFAULT, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(updSql);
      statement.setString(1, name);
      statement.setString(2, lastName);
      statement.setByte(3, age);
      boolean wasUpdate = executor.execUpdate(statement);
      connection.commit();
      System.out.printf("Юзер с именем %s и фамилией %s был добавлен%n", name, lastName);
      return wasUpdate;
    } catch (SQLException e) {
      try {
        connection.rollback();
      } catch (SQLException ignore) {
      }
      System.out.println("Ошибка при добавлении юзера");
      e.printStackTrace();
    } finally {
      try {
        connection.setAutoCommit(true);
      } catch (SQLException ignore) {
      }
    }
    return false;
  }

  public boolean createTable() {
    String createTable = "CREATE TABLE IF NOT EXISTS example " +
        "(id SERIAL PRIMARY KEY, name varchar(256), lastName varchar(256), age smallint)";
    PreparedStatement statement;
    try {
      statement = connection.prepareStatement(createTable);
      return executor.execUpdate(statement);
    } catch (SQLException e) {
      System.out.println("Ошибка при создании таблицы");
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public void dropTable(){
    try {
      PreparedStatement statement = connection.prepareStatement("drop table if exists example ");
      executor.execUpdate(statement);
    }catch (SQLException e){
      e.printStackTrace();
      System.out.println("Не удалось удалить таблицу");
    }
  }

  @Override
  public List<User> getAllUsers() {
    try {
      List<User> users = new ArrayList<>();
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM example");
      return executor.execQuery(statement, result -> {
        while (result.next()) {
          users.add(new User(result.getLong(1), result.getString(2), result.getString(3)
              , result.getByte(4)));
        }
        return users;
      });
    } catch (Exception e) {
      System.out.println("Ошибка при поиске юзеров");
    }
    return new ArrayList<>();
  }
}
