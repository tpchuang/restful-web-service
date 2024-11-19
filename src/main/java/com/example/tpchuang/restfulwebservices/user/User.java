package com.example.tpchuang.restfulwebservices.user;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Represents a user with an ID, name, and birthdate.
 */
public record User(Integer id,
                   @Size(min = 2, message = ERROR_NAME_SIZE) String name,
                   @Past(message = ERROR_INVALID_BIRTHDATE) LocalDate birthDate) {

  public static final String ERROR_NAME_SIZE = "Name should have at least 2 characters";
  public static final String ERROR_INVALID_BIRTHDATE = "Birthdate should be in the past";

  /**
   * Creates a new User with the specified ID.
   *
   * @param id the ID to assign to the new User
   * @return a new User instance with the specified ID
   */
  public User withId(Integer id) {
    return new User(id, name(), birthDate());
  }
}
