package com.example.tpchuang.restfulwebservices.helloworld;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class HelloWorldBeanTest {

  @ParameterizedTest
  @ValueSource(strings = {"Hello World!", "Good Morning!"})
  void testHelloWorldBean(String input) {
    HelloWorldBean helloWorldBean = new HelloWorldBean(input);
    assertThat(helloWorldBean.message()).isEqualTo(input);
  }
}
