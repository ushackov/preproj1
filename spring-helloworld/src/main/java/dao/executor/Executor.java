package dao.executor;

import java.sql.*;

public class Executor {

  public boolean execUpdate(PreparedStatement stmt) throws SQLException {
    int count = stmt.executeUpdate();
    stmt.close();
    return count != 0;
  }

  public <T> T execQuery(PreparedStatement stmt,
                         ResultHandler<T> handler)
      throws SQLException {
    ResultSet result = stmt.executeQuery();
    T value = handler.handle(result);
    result.close();
    return value;
  }
}
