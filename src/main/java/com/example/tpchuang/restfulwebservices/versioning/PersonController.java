package com.example.tpchuang.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

  public static final String X_API_VERSION = "X-Api-Version";
  public static final String PARAM_VERSION = "version";

  @GetMapping(path = "/person")
  public PersonV1 personDefault() {
    return personV1();
  }

  @GetMapping(path = "/person", headers = X_API_VERSION + "=1")
  public PersonV1 personV1() {
    return new PersonV1("Bob Charlie");
  }

  @GetMapping(path = "/person", headers = X_API_VERSION + "=2")
  public PersonV2 personV2() {
    return new PersonV2(new Name("Bob", "Charlie"));
  }

  @GetMapping(path = "/person-param")
  public PersonV1 personParamDefault() {
    return personParamV1();
  }

  @GetMapping(path = "/person-param", params = PARAM_VERSION + "=1")
  public PersonV1 personParamV1() {
    return new PersonV1("Bob Charlie");
  }

  @GetMapping(path = "/person-param", params = PARAM_VERSION + "=2")
  public PersonV2 personParamV2() {
    return new PersonV2(new Name("Bob", "Charlie"));
  }
}
