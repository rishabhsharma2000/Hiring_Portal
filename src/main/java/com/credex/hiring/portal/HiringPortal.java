/**
 * Copyright (c) 2023 Your Credex Technology. 
 * All rights reserved.
 */
package com.credex.hiring.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication()
@EnableSwagger2

public class HiringPortal {

  public static void main(String[] args) {
    SpringApplication.run(HiringPortal.class, args);
  }
}
