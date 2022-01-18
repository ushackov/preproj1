package dao.utils;

import org.hibernate.SessionFactory;
import model.User;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionFactoryUtils {

  protected SessionFactory createSessionFactory(Configuration configuration) {
    StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
    builder.applySettings(configuration.getProperties());

    ServiceRegistry serviceRegistry = builder.build();
    return configuration.buildSessionFactory(serviceRegistry);
  }

  protected Configuration getPostgresConfiguration() {
    Configuration configuration = new Configuration()
        .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL82Dialect")
        .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
        .setProperty("hibernate.connection.username", "postgres")
        .setProperty("hibernate.connection.password", "postgres")
        .setProperty("hibernate.connection.url","jdbc:postgresql://localhost:5432/postgres")
        .setProperty("hbm2ddl.auto", "update")
        .setProperty("show_sql", "true")
        .setProperty("connection_pool_size", "1");
    configuration.addAnnotatedClass(User.class);
    return configuration;
  }
}
