To enable Spring Security (step 37):

1. Add the following dependency to `pom.xml` file:
   ```xml
   <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-security</artifactId>
     <version>${spring.boot.version}</version>
   </dependency>
   ```
2. Create a configuration class:
   ```java
   package com.example.tpchuang.restfulwebservices.security;
   
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   import org.springframework.security.config.Customizer;
   import org.springframework.security.config.annotation.web.builders.HttpSecurity;
   import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
   import org.springframework.security.web.SecurityFilterChain;
   
   @Configuration
   public class SpringSecurityConfiguration {
   
     @Bean
     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
       http.httpBasic(Customizer.withDefaults());
       http.csrf(AbstractHttpConfigurer::disable);
       return http.build();
     }
   }
   ```
