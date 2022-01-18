package dao;

import dao.utils.SessionFactoryUtils;
import model.User;
import org.hibernate.*;

import java.util.ArrayList;
import java.util.List;

public class HibernateDAO extends SessionFactoryUtils implements DAO {

  private final SessionFactory sessionFactory;

  public HibernateDAO() {
    this.sessionFactory = createSessionFactory(getPostgresConfiguration());
  }

  @Override
  public User get(String lastName) throws HibernateException {
    Session session = sessionFactory.openSession();
    try {
      String sql = "FROM User WHERE lastName= :lastName";
      Query query = session.createQuery(sql);
      query.setParameter("lastName", lastName);
      User example = (User) query.uniqueResult();
      session.flush();
      return example;
    } catch (Exception e) {
      throw new HibernateException(e);
    } finally {
      session.close();
    }
  }

  @Override
  public boolean removeUserByLastName(String lastName) {
    Session session = sessionFactory.openSession();
    try {
      String sql = "DELETE FROM User u WHERE u.lastName= :lastName";
      Query query = session.createQuery(sql);
      query.setString("lastName", lastName).executeUpdate();
      session.flush();
    } catch (Exception e) {
      System.out.println("Юзер не удален");
      e.printStackTrace();
    } finally {
      session.close();
    }
    return false;
  }

  @Override
  public boolean insertUser(String name, String lastName, byte age) {
    Session session = sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();
    try {
      boolean isSaved = (Long) session.save(new User(name, lastName, age)) != 0;
      session.flush();
      transaction.commit();
      System.out.printf("Юзер с именем %s и фамилией %s был добавлен%n", name, lastName);
      return isSaved;
    } catch (Exception e) {
      try {
        transaction.rollback();
      } catch (Exception ignore) {
        System.out.println("Ошибка при добавлении юзера");
        e.printStackTrace();
      }
    } finally {
      session.close();
    }
    return false;
  }

  @Override
  public void dropTable() {
    Session session = sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();
    try {
      session.createSQLQuery("DROP TABLE IF EXISTS example").executeUpdate();
      session.flush();
      transaction.commit();
    } catch (HibernateException e) {
      System.out.println("Ошибка удалении таблицы");
      e.printStackTrace();
    } finally {
      session.close();
    }
  }

  @Override
  public boolean createTable() {
    Session session = sessionFactory.openSession();
    try {
      session.createSQLQuery("CREATE TABLE IF NOT EXISTS example " +
              "(id SERIAL PRIMARY KEY, name varchar(256), lastName varchar(256), age smallint)")
          .executeUpdate();
    } catch (HibernateException e) {
      System.out.println("Ошибка создании таблицы");
      e.printStackTrace();
    }
    finally {
      session.close();
    }
    return false;
  }

  @Override
  public List<User> getAllUsers() {
    String sql = "FROM User";
    Session session = sessionFactory.openSession();
    try {
      Query query = session.createQuery(sql);
      List<User> list = query.list();
      session.flush();
      return list;
    } catch (Exception e) {
      System.out.println("Произошло исключение при поиске всех юзеров");
    } finally {
      session.close();
    }
    return new ArrayList<>();
  }
}
