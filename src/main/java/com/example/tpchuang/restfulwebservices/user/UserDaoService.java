package com.example.tpchuang.restfulwebservices.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Service class for managing users.
 */
@Component
public class UserDaoService {

  private final List<User> users = new ArrayList<>();
  private int lastUserId = 1;

  /**
   * Initializes the service with some default users.
   */
  public UserDaoService() {
    final int dummyId = 0;
    save(new User(dummyId, "Adam", LocalDate.parse("1990-01-01")));
    save(new User(dummyId, "Eve", LocalDate.parse("1990-01-02")));
    save(new User(dummyId, "Jim", LocalDate.now().minusYears(25)));
  }

  /**
   * Retrieves all users.
   *
   * @return a list of all users
   */
  public List<User> findAll() {
    return users;
  }

  /**
   * Retrieves a user by their ID.
   *
   * @param id the ID of the user to retrieve
   * @return the user with the specified ID, or null if no such user exists
   */
  public User get(int id) {
    return users.stream().filter(user -> user.id() == id).findFirst().orElse(null);
  }

  /**
   * Saves a new user.
   *
   * @param user the user to save
   * @return the saved user with an assigned ID
   */
  public User save(User user) {
    user = user.withId(lastUserId++);
    users.add(user);
    return user;
  }

  /**
   * Deletes a user by their ID.
   *
   * @param id the ID of the user to delete
   */
  public void deleteById(int id) {
    users.removeIf(user -> user.id() == id);
  }
}
