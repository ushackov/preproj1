package service;

import dao.DAO;
import dao.HibernateDAO;
import model.User;

import java.util.List;

public class Service implements DAO {
  private final DAO dao;

  public Service() {
//    this.dao = new JdbcDAO();
    this.dao = new HibernateDAO();
  }

  public User get(String login) {
    return dao.get(login);
  }

  @Override
  public boolean removeUserByLastName(String lastName) {
    return dao.removeUserByLastName(lastName);
  }

  public boolean insertUser(String name, String lastName, byte age) {
    return dao.insertUser(name, lastName, age);
  }

  @Override
  public boolean createTable() {
    return dao.createTable();
  }

  @Override
  public List<User> getAllUsers() {
    return dao.getAllUsers();
  }

  @Override
  public void dropTable() {
    dao.dropTable();
  }
}


