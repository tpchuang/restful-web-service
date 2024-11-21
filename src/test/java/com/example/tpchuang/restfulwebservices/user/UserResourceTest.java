package com.example.tpchuang.restfulwebservices.user;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserResource.class)
class UserResourceTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserDaoService userDaoService;

  @Test
  void findAllUsers() throws Exception {
    List<User> users = List.of(
        new User(1, "John Doe", LocalDate.of(1980, 1, 1)),
        new User(2, "Jane Doe", LocalDate.of(1985, 2, 2)));
    when(userDaoService.findAll()).thenReturn(users);

    mockMvc.perform(get("/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].name").value("John Doe"))
        .andExpect(jsonPath("$[1].name").value("Jane Doe"));
  }

  @Test
  void getUser_ShouldReturnUser() throws Exception {
    User user = new User(1, "John Wow", LocalDate.of(1970, 7, 7));
    when(userDaoService.get(1)).thenReturn(user);

    mockMvc.perform(get("/users/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(user.name()))
        .andExpect(jsonPath("$.birthDate").value(
            user.birthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
        .andExpect(jsonPath("$._links.all-users").exists());
  }

  @Test
  void getUser_ShouldReturnNotFound() throws Exception {
    when(userDaoService.get(999)).thenReturn(null);

    mockMvc.perform(get("/users/999"))
        .andExpect(status().isNotFound());
  }

  @Test
  void createUser_ShouldReturnCreatedStatus() throws Exception {
    User newUser = new User(null, "Alice Smith", LocalDate.of(1990, 3, 3));
    User savedUser = newUser.withId(3);
    when(userDaoService.save(newUser)).thenReturn(savedUser);

    String userJson = "{\"name\":\"Alice Smith\",\"birthDate\":\"1990-03-03\"}";

    mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/users/3"));
  }

  @Test
  void deleteUser() throws Exception {
    doNothing().when(userDaoService).deleteById(1);

    mockMvc.perform(delete("/users/1"))
        .andExpect(status().isOk());
  }
}
