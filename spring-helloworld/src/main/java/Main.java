import model.User;
import service.Service;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    Service service = new Service();
    service.dropTable();
    service.createTable();
    service.insertUser("Andrey", "Ushakov", (byte) 33);
    service.insertUser("Tolya", "Marandyuk", (byte) 23);
    service.insertUser("Karina", "Shmakova", (byte) 30);
    List<User> list = service.getAllUsers();
    list.forEach(s -> System.out.println(s.toString()));
    service.removeUserByLastName("Ushakov");
    System.out.println();
    service.getAllUsers()
        .forEach(s -> System.out.println(s.toString()));
    service.dropTable();
  }
}
