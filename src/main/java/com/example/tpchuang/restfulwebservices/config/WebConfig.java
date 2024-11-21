package com.example.tpchuang.restfulwebservices.config;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
@Getter
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    YAMLMapper yamlMapper = new YAMLMapper();
    yamlMapper.registerModule(new JavaTimeModule());
    converters.add(new AbstractJackson2HttpMessageConverter(yamlMapper,
        new MediaType("application", "x-yaml", StandardCharsets.UTF_8)) {
    });
  }
}
