package com.example.tpchuang.restfulwebservices.util;

import static com.example.tpchuang.restfulwebservices.util.LogHelper.TEXT_NULL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class LogHelperTest {

  @Test
  void objectToString_defaultObjectMapper() {
    String result = LogHelper.objectToString(new TestObject("test", 123));
    assertThat(result).isEqualTo("{\"name\":\"test\",\"value\":123}");
  }

  @Test
  void objectToString() {
    ObjectMapper objectMapper = new ObjectMapper();
    String result = LogHelper.objectToString(objectMapper, new TestObject("test2", 246));
    assertThat(result).isEqualTo("{\"name\":\"test2\",\"value\":246}");
  }

  @Test
  void objectToString_null() {
    ObjectMapper objectMapper = new ObjectMapper();
    assertThat(LogHelper.objectToString(objectMapper, null)).isEqualTo(TEXT_NULL);
  }

  @Test
  void objectToString_exception() throws JsonProcessingException {
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.writeValueAsString(any())).thenThrow(new JsonProcessingException("error") {
    });
    TestObject testObject = new TestObject("test3", 369);
    String result = LogHelper.objectToString(objectMapper, testObject);
    assertThat(result).isEqualTo(testObject.toString());
  }

  private record TestObject(String name, int value) {

  }
}