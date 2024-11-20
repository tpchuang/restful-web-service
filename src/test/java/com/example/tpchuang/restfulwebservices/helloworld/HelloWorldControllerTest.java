package com.example.tpchuang.restfulwebservices.helloworld;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(HelloWorldController.class)
class HelloWorldControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void helloWorld()
      throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/hello-world"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("Hello World!"));
  }

  @Test
  void helloWorldBean()
      throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/hello-world-bean"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath("$.message").value("Hello World!"));
  }

  @ParameterizedTest
  @ValueSource(strings = {"Charmander", "Bulbasaur", "Squirtle"})
  void helloWorldPathVariable(String name) throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/hello-world-path-variable/" + name))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath("$.message").value("Hello " + name + "!"));
  }

  @Test
  void greetings() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/greetings"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("Good Morning!"));
  }

  @ParameterizedTest
  @CsvSource({"en, Good Morning!", "nl, Goedemorgen!"})
  void greetingsWithLocale(String locale, String expected) throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/greetings").header("Accept-Language", locale))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(expected));
  }
}
