package com.example.tpchuang.restfulwebservices.config;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.nio.charset.StandardCharsets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

@Configuration
public class YamlConfig {

  @Bean
  public HttpMessageConverter<Object> yamlHttpMessageConverter() {
    YAMLMapper yamlMapper = new YAMLMapper();
    yamlMapper.registerModule(new JavaTimeModule());
    return new AbstractJackson2HttpMessageConverter(yamlMapper,
        new MediaType("application", "x-yaml", StandardCharsets.UTF_8)) {
    };
  }
}