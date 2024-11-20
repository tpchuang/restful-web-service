package com.example.tpchuang.restfulwebservices.versioning;

import static com.example.tpchuang.restfulwebservices.versioning.PersonController.PARAM_VERSION;
import static com.example.tpchuang.restfulwebservices.versioning.PersonController.X_API_VERSION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void personDefault() throws Exception {
    mockMvc.perform(get("/person"))
        .andExpect(status().isOk())
        .andExpect(content().json("{\"name\":\"Bob Charlie\"}"));
  }

  @Test
  void personV1() throws Exception {
    mockMvc.perform(get("/person").header(X_API_VERSION, "1"))
        .andExpect(status().isOk())
        .andExpect(content().json("{\"name\":\"Bob Charlie\"}"));
  }

  @Test
  void personV2() throws Exception {
    mockMvc.perform(get("/person").header(X_API_VERSION, "2"))
        .andExpect(status().isOk())
        .andExpect(content().json("{\"name\":{\"firstName\":\"Bob\",\"lastName\":\"Charlie\"}}"));
  }

  @Test
  void personParamDefault() throws Exception {
    mockMvc.perform(get("/person-param"))
        .andExpect(status().isOk())
        .andExpect(content().json("{\"name\":\"Bob Charlie\"}"));
  }

  @Test
  void personParamV1() throws Exception {
    mockMvc.perform(get("/person-param").param(PARAM_VERSION, "1"))
        .andExpect(status().isOk())
        .andExpect(content().json("{\"name\":\"Bob Charlie\"}"));
  }

  @Test
  void personParamV2() throws Exception {
    mockMvc.perform(get("/person-param").param(PARAM_VERSION, "2"))
        .andExpect(status().isOk())
        .andExpect(content().json("{\"name\":{\"firstName\":\"Bob\",\"lastName\":\"Charlie\"}}"));
  }
}
