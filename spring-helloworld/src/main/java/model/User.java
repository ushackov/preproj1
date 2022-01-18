package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "example")
public class User implements Serializable {
  private static final long serialVersionUID = -8706689714326132798L;

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(updatable = false)
  private String name;

  @Column(unique = true, updatable = false)
  private String lastName;

  @Column
  private byte age;

  @SuppressWarnings("UnusedDeclaration")
  public User() {
  }

  @SuppressWarnings("UnusedDeclaration")
  public User(long id, String name, String pass, byte age) {
    this.id = id;
    this.name = name;
    this.lastName = pass;
    this.age = age;
  }

  @SuppressWarnings("UnusedDeclaration")
  public User(String name, String pass, byte age) {
    this.setId(-1);
    this.name = name;
    this.lastName = pass;
    this.age = age;
  }

  @SuppressWarnings("UnusedDeclaration")
  public User(String name) {
    this.setId(-1);
    this.setName(name);
  }

  @SuppressWarnings("UnusedDeclaration")
  public void setId(long id) {
    this.id = id;
  }

  @SuppressWarnings("UnusedDeclaration")
  public void setName(String name) {
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getLastName() {
    return lastName;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name +
        ", lastName='" + lastName + '\'' +
        '}';
  }

  public byte getAge() {
    return age;
  }

  public void setAge(byte age) {
    this.age = age;
  }
}

