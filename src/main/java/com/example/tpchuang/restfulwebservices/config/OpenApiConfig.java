package com.example.tpchuang.restfulwebservices.config;

import com.example.tpchuang.restfulwebservices.util.LogHelper;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class OpenApiConfig {

  private static final String CONTENT_TYPE_JSON = "application/json";
  private static final String CONTENT_TYPE_YAML = "application/x-yaml";
  private static final String CONTENT_TYPE_ANY = "*/*";
  private static final String JSON_TYPE_STRING = "string";

  /**
   * The customizer of Swagger UI.
   *
   * @return The OpenAPI object
   */
  @Bean
  public OpenAPI customOpenAPI() {
    log.info("Creating custom OpenAPI bean");
    return new OpenAPI().info(new Info().title("RESTful Web Services").version("1.0"));
  }

  /**
   * The customizer of Swagger UI operations.
   *
   * @return The OpenApiCustomizer object
   */
  @Bean
  public OpenApiCustomizer customizeOpenApi() {
    log.info("Creating custom OpenApiCustomizer bean");
    return openApi -> openApi.getPaths().values()
        .forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
          configureRequestBody(operation);
          configureApiResponses(operation);
        }));
  }

  /**
   * Configures the request body to support YAML in addition to JSON. Only the operations that
   * expect a request body are considered.
   *
   * @param operation The operation to configure.
   */
  private void configureRequestBody(Operation operation) {
    RequestBody requestBody = operation.getRequestBody();
    if (requestBody == null) {
      return;
    }
    Content content = requestBody.getContent();
    MediaType mediaType = content.get(CONTENT_TYPE_JSON) != null ? content.get(CONTENT_TYPE_JSON)
        : new MediaType().schema(new Schema<>());
    content.addMediaType(CONTENT_TYPE_YAML, mediaType);
  }

  /**
   * Configures the API responses to support both JSON and YAML. Only the operations that have
   * existing content schema are considered.
   *
   * @param operation The operation to configure.
   */
  private void configureApiResponses(Operation operation) {
    ApiResponses apiResponses = operation.getResponses();
    log.info("Configuring API responses for operation: {}", operation.getOperationId());
    for (Map.Entry<String, ApiResponse> entry : apiResponses.entrySet()) {
      String key = entry.getKey();
      ApiResponse apiResponse = entry.getValue();
      Content content = apiResponse.getContent();
      log.info("  {} {}", key, LogHelper.objectToString(content));
      if (content != null) {
        MediaType mediaType = content.get(CONTENT_TYPE_ANY);
        if (mediaType != null && !JSON_TYPE_STRING.equals(mediaType.getSchema().getType())) {
          apiResponse.content(new Content().addMediaType(CONTENT_TYPE_JSON, mediaType)
              .addMediaType(CONTENT_TYPE_YAML, mediaType));
        }
        operation.getResponses().addApiResponse(key, apiResponse);
      }
    }
  }
}