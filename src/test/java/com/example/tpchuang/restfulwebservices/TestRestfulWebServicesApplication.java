package com.example.tpchuang.restfulwebservices;

import org.springframework.boot.SpringApplication;

public class TestRestfulWebServicesApplication {

  public static void main(String[] args) {
    SpringApplication.from(RestfulWebServicesApplication::main)
        .with(TestContainersConfiguration.class).run(args);
  }

}
