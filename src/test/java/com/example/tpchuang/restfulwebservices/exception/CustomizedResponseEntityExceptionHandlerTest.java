package com.example.tpchuang.restfulwebservices.exception;

import static com.example.tpchuang.restfulwebservices.user.User.ERROR_INVALID_BIRTHDATE;
import static com.example.tpchuang.restfulwebservices.user.User.ERROR_NAME_SIZE;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.tpchuang.restfulwebservices.user.User;
import com.example.tpchuang.restfulwebservices.user.UserNotFoundException;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebMvcTest(controllers = CustomizedResponseEntityExceptionHandlerTest.DummyController.class)
class CustomizedResponseEntityExceptionHandlerTest {

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(new DummyController())
        .setControllerAdvice(new CustomizedResponseEntityExceptionHandler())
        .build();
  }

  @Test
  void testHandleAllExceptions() throws Exception {
    mockMvc.perform(get("/test-exception")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.message").value("Simulated exception"))
        .andExpect(jsonPath("$.details").exists())
        .andExpect(jsonPath("$.timestamp").exists());
  }

  @Test
  void handleUserNotFoundExceptions2_ShouldReturnNotFound() throws Exception {
    mockMvc.perform(get("/users/999"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.details").exists())
        .andExpect(jsonPath("$.timestamp").exists());
  }

  @Test
  void handleMethodArgumentNotValid_ShouldReturnUnprocessableEntity() throws Exception {
    String futureDate = LocalDate.now().plusYears(1)
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    String invalidUserJson = "{\"name\":\"X\",\"birthDate\":\"" + futureDate + "\"}";
    mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidUserJson))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.message").value(containsString(ERROR_NAME_SIZE)))
        .andExpect(jsonPath("$.message").value(containsString(ERROR_INVALID_BIRTHDATE)))
        .andExpect(jsonPath("$.details").exists())
        .andExpect(jsonPath("$.timestamp").exists())
        .andReturn();
  }

  @RestController
  public static class DummyController {

    @GetMapping("/test-exception")
    public String triggerException() {
      throw new RuntimeException("Simulated exception");
    }

    @GetMapping("/users/999")
    public String triggerUserNotFoundException() {
      throw new UserNotFoundException("User not found exception");
    }

    @PostMapping("/users")
    public String createUser(@Valid @RequestBody User user) {
      return "User created";
    }
  }
}
