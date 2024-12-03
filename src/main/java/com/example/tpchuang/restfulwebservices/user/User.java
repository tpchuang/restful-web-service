package com.example.tpchuang.restfulwebservices.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

/**
 * Represents a user with an ID, name, and birthdate.
 * <p>
 * It is possible to (<a href="https://www.baeldung.com/spring-jpa-java-records">use Java records
 * with JPA</a>). However, it complicates the implementation.
 */
@Entity(name = "user_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

  public static final String ERROR_NAME_SIZE = "Name should have at least 2 characters";
  public static final String ERROR_INVALID_BIRTHDATE = "Birthdate should be in the past";

  @Id
  @GeneratedValue
  @With
  private Integer id;

  @Size(min = 2, message = ERROR_NAME_SIZE)
  private String name;

  @Past(message = ERROR_INVALID_BIRTHDATE)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate birthDate;
}
