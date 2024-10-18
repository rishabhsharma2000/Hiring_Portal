/**
 * Copyright (c) 2023 Your Credex Technology. 
 * All rights reserved.
 */
package com.credex.hiring.portal.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import com.credex.hiring.portal.utility.Constants;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements Constants {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
        .paths(PathSelectors.any()).build()
        .securitySchemes(Arrays.asList(apiKey()))
        .securityContexts(Arrays.asList(securityContext()));
  }

  @Bean
  SecurityContext securityContext() {
    return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(PathSelectors.any()) // Apply security to all paths
            .build();
  }

  List<SecurityReference> defaultAuth() {
    return Arrays.asList(
        new SecurityReference(JWT, new AuthorizationScope[] { new AuthorizationScope(GLOBAL, ACCESS_EVERYTHING) }));
  }

  private ApiKey apiKey() {
    return new ApiKey(JWT, HttpHeaders.AUTHORIZATION, HEADER);
  }

}