package com.example.tpchuang.restfulwebservices.helloworld;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HelloWorldController {

  private final MessageSource messageSource;

  @GetMapping(path = "/hello-world")
  public String helloWorld() {
    return "Hello World!";
  }

  @GetMapping(path = "/hello-world-bean")
  public HelloWorldBean helloWorldBean() {
    return new HelloWorldBean("Hello World!");
  }

  @GetMapping(path = "/hello-world-path-variable/{name}")
  public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
    return new HelloWorldBean(String.format("Hello %s!", name));
  }

  @GetMapping(path = "/greetings")
  public String greetings() {
    return messageSource.getMessage("good.morning.message", null, "Hi!",
        LocaleContextHolder.getLocale());
  }
}
