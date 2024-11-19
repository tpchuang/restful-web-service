package com.example.tpchuang.restfulwebservices.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * User not found exception.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

  /**
   * Constructs a new user not found exception with the specified detail message.
   *
   * @param message the detail message
   */
  public UserNotFoundException(String message) {
    super(message);
  }
}
