package com.example.tpchuang.restfulwebservices.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LogHelper {

  public static final String TEXT_NULL = "null";
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static String objectToString(Object object) {
    return objectToString(objectMapper, object);
  }

  public static String objectToString(@NonNull ObjectMapper objectMapper, Object object) {
    if (object == null) {
      return TEXT_NULL;
    }
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      return object.toString();
    }
  }
}
