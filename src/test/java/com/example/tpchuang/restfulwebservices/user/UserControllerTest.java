package com.example.tpchuang.restfulwebservices.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void findAllUsers() throws Exception {
    mockMvc.perform(get("/users").contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith("application/json"))
        .andExpect(jsonPath("$.length()").value(3));
  }

  @Test
  void getUser() throws Exception {
    mockMvc.perform(get("/users/10003"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(10003))
        .andExpect(jsonPath("$.name").value("Owen"))
        .andExpect(jsonPath("$.birthDate").value("1990-01-02"));
  }

  @Test
  void getUser_notFound() throws Exception {
    mockMvc.perform(get("/users/999"))
        .andExpect(status().isNotFound());
  }

  @Test
  void createUser() throws Exception {
    String userJson = "{\"name\":\"Alice Smith\",\"birthDate\":\"1990-03-03\"}";
    mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/users/1"));
    mockMvc.perform(get("/users/1")
            .contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Alice Smith"))
        .andExpect(jsonPath("$.birthDate").value("1990-03-03"));
  }

  @Test
  void deleteUser() throws Exception {
    mockMvc.perform(get("/users/10002"))
        .andExpect(status().isOk());
    mockMvc.perform(delete("/users/10002"))
        .andExpect(status().isOk());
    mockMvc.perform(get("/users/10002"))
        .andExpect(status().isNotFound());
  }

}
