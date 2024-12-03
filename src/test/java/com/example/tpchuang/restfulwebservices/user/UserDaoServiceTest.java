package com.example.tpchuang.restfulwebservices.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserDaoServiceTest {

  private UserDaoService userDaoService;

  @BeforeEach
  void setUp() {
    userDaoService = new UserDaoService();
  }

  @Test
  void testFindAll() {
    List<User> users = userDaoService.findAll();
    assertThat(users).hasSize(3);
  }

  @Test
  void testGetUserById() {
    User user = userDaoService.get(1);
    assertThat(user).isNotNull();
    assertThat(user.getId()).isEqualTo(1);
  }

  @Test
  void testSaveUser() {
    User newUser = new User(0, "Jane Doe", LocalDate.of(1995, 5, 15));
    User savedUser = userDaoService.save(newUser);

    assertThat(savedUser.getId()).isEqualTo(4);
    assertThat(savedUser.getName()).isEqualTo(newUser.getName());
    assertThat(savedUser.getBirthDate()).isEqualTo(newUser.getBirthDate());
  }

  @Test
  void testDeleteUserById() {
    assertThat(userDaoService.findAll()).hasSize(3);
    assertThat(userDaoService.get(1)).isNotNull();
    userDaoService.deleteById(1);
    assertThat(userDaoService.get(1)).isNull();
    assertThat(userDaoService.findAll()).hasSize(2);
  }
}
