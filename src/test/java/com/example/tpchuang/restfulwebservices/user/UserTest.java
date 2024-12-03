package com.example.tpchuang.restfulwebservices.user;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserTest {

  private static Validator validator;

  @BeforeAll
  static void setUp() {
    try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
      validator = factory.getValidator();
    }
  }

  @Test
  void testUserCreation() {
    User user = new User(1, "John Doe", LocalDate.of(1990, 1, 1));

    assertThat(user.getId()).isEqualTo(1);
    assertThat(user.getName()).isEqualTo("John Doe");
    assertThat(user.getBirthDate()).isEqualTo(LocalDate.of(1990, 1, 1));
  }

  @Test
  void testWithId() {
    User user = new User(1, "John Doe", LocalDate.of(1990, 1, 1));
    User newUser = user.withId(2);

    assertThat(newUser.getId()).isEqualTo(2);
    assertThat(newUser.getName()).isEqualTo(user.getName());
    assertThat(newUser.getBirthDate()).isEqualTo(user.getBirthDate());
  }

  @Test
  void testNameTooShort() {
    User user = new User(1, "J", LocalDate.of(1990, 1, 1));
    Set<ConstraintViolation<User>> violations = validator.validate(user);

    assertThat(violations).hasSize(1);
    assertThat(violations.iterator().next().getMessage()).isEqualTo(User.ERROR_NAME_SIZE);
  }

  @Test
  void testBirthDateInFuture() {
    User user = new User(1, "John Doe", LocalDate.now().plusDays(1));
    Set<ConstraintViolation<User>> violations = validator.validate(user);

    assertThat(violations).hasSize(1);
    assertThat(violations.iterator().next().getMessage()).isEqualTo(User.ERROR_INVALID_BIRTHDATE);
  }

  @Test
  void testValidUser() {
    User user = new User(1, "John Doe", LocalDate.of(1990, 1, 1));
    Set<ConstraintViolation<User>> violations = validator.validate(user);

    assertThat(violations).isEmpty();
  }
}
