package dao;

import model.User;

import java.util.List;

public interface DAO {

  boolean insertUser(String name, String lastName, byte age);

  User get(String login);

  boolean removeUserByLastName(String lastName);

  boolean createTable();

  List<User> getAllUsers();

  void dropTable();
}
