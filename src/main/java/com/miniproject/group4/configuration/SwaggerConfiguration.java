package com.miniproject.group4.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfiguration {

  public static final String AUTHORIZATION_HEADER = "Authorization";

  /**
   * To be shown in the swagger-ui
   */
  private ApiInfo apiInfo() {
    return new ApiInfo("Swagger2 Configuration - Group 4",
        "Here is our implementation of swagger2 that contains payroll and user controller" +
                "\nPresented by: " +
                "\nDean Vonn Angelo Enalpe" +
                "\nDondon Jeric Co" +
                "\nJohn Michael Flores",
        "1.1",
        "Terms of service: For mini-project presentation",
        new Contact("Group 4", "", "Group 4@domain.com"),
        "License of API",
        "API license URL",
        Collections.emptyList());
  }

  /**
   * Defining the security config used for swagger.
   * <br>
   * As well as defining which controller will be show in the swagger used.
   */
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .securityContexts(Arrays.asList(securityContext()))
        .securitySchemes(Arrays.asList(apiKey()))
        .select()
        .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
        //.apis(RequestHandlerSelectors.basePackage("com.miniproject.group4.controller"))
        .paths(PathSelectors.any())
        .build();
  }

  /**
   * Defining the apiKey to be used by swagger and how it is pass by the client.
   */
  private ApiKey apiKey() {
    return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
  }

  /**
   * Building the security context of the authorized user.
   */
  private SecurityContext securityContext() {
    return SecurityContext.builder()
        .securityReferences(defaultAuth())
        .build();
  }

  /**
   *
   * Defining the scope the authorized user can access on swagger.
   * <br>
   * As well as what kind of token to be used.
   *
   */
  List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope
        = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
  }

}