package com.example.tpchuang.restfulwebservices.user;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
public class UserResource {

  private final UserDaoService userDaoService;

  /**
   * Retrieves all users.
   *
   * @return a list of all users
   */
  @GetMapping(path = "/users")
  public List<User> findAllUsers() {
    return userDaoService.findAll();
  }

  /**
   * Retrieves a user by their ID.
   *
   * @param id the ID of the user to retrieve
   * @return the user with the specified ID, or null if no such user exists
   */
  @GetMapping(path = "/users/{id}")
  public User getUser(@PathVariable int id) {
    User user = userDaoService.get(id);
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
  @PostMapping(path = "/users")
  public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
    User savedUser = userDaoService.save(user);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedUser.id()).toUri();
    return ResponseEntity.created(location).build();
  }

  /**
   * Deletes a user by their ID.
   *
   * @param id the ID of the user to delete
   */
  @DeleteMapping(path = "/users/{id}")
  public void deleteUser(@PathVariable int id) {
    userDaoService.deleteById(id);
  }
  
}