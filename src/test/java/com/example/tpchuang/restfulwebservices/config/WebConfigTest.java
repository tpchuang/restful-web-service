package com.example.tpchuang.restfulwebservices.config;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.tpchuang.restfulwebservices.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

@SpringBootTest
class WebConfigTest {

  @Autowired
  private WebConfig webConfig;

  @Test
  void testConfigureMessageConverters() throws IOException {
    List<HttpMessageConverter<?>> converters = new java.util.ArrayList<>();
    webConfig.configureMessageConverters(converters);

    assertThat(converters).hasSize(1);
    HttpMessageConverter<?> converter = converters.getFirst();
    assertThat(converter).isInstanceOf(AbstractJackson2HttpMessageConverter.class);

    AbstractJackson2HttpMessageConverter jacksonConverter = (AbstractJackson2HttpMessageConverter) converter;
    ObjectMapper objectMapper = jacksonConverter.getObjectMapper();
    assertThat(objectMapper).isInstanceOf(YAMLMapper.class);
    assertThat(objectMapper.getRegisteredModuleIds()).contains(
        com.fasterxml.jackson.datatype.jsr310.PackageVersion.VERSION.getArtifactId());

    MediaType mediaType = jacksonConverter.getSupportedMediaTypes().getFirst();
    assertThat(mediaType).isEqualTo(new MediaType("application", "x-yaml", StandardCharsets.UTF_8));

    InputStream inputStream = getClass().getResourceAsStream("user.yaml");
    User user = objectMapper.readValue(inputStream, User.class);
    assertThat(user).isNotNull();
  }
}
