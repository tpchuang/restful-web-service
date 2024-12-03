package com.example.tpchuang.restfulwebservices.user;

import com.example.tpchuang.restfulwebservices.jpa.UserRepository;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing users.
 */
@RestController
@RequiredArgsConstructor
public class UserController {

  private static final String APPLICATION_X_YAML = "application/x-yaml";

  private final UserRepository userRepository;

  /**
   * Retrieves all users.
   *
   * @return a list of all users
   */
  @GetMapping(path = "/users")
  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  /**
   * Retrieves a user by their ID.
   *
   * @param id the ID of the user to retrieve
   * @return the user with the specified ID
   * @throws UserNotFoundException if no user with the specified ID exists
   */
  @GetMapping(path = "/users/{id}")
  public User getUser(@PathVariable int id) {
    User user = userRepository.findById(id).orElse(null);
    if (user == null) {
      throw new UserNotFoundException("id: " + id);
    }
    return user;
  }

  /**
   * Creates a new user.
   *
   * @param user the user to create. User ID will be assigned.
   * @return a ResponseEntity with the location of the created user
   */
  @PostMapping(path = "/users", consumes = {MediaType.APPLICATION_JSON_VALUE, APPLICATION_X_YAML})
  public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
    User savedUser = userRepository.save(user);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedUser.getId()).toUri();
    return ResponseEntity.created(location).build();
  }

  /**
   * Deletes a user by their ID.
   *
   * @param id the ID of the user to delete
   */
  @DeleteMapping(path = "/users/{id}")
  public void deleteUser(@PathVariable int id) {
    userRepository.deleteById(id);
  }

}
