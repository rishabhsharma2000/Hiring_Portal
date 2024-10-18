/**
 * Copyright (c) 2023 Your Credex Technology. 
 * All rights reserved.
 */
package com.credex.hiring.portal.utility;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

public class Utility {

  public static void updateFields(Object toUpdate, Object fromUpdate, Class<?> entity) {
    Field[] fields = entity.getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      try {
        Object updatedValue = field.get(fromUpdate);
        if (Objects.nonNull(updatedValue)) {
          field.set(toUpdate, updatedValue);
        }
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e.getLocalizedMessage());
      }
    }
  }

  public static ResponseEntity<String> responseCreate(HttpStatus statusCode) {
    JSONObject response = new JSONObject();
    response.put("Create", "True");
    response.put("message", "Data Inserted Successfully");
    return new ResponseEntity<String>(response.toString(), statusCode);
  }

  public static ResponseEntity<String> responseSuccess() {
    JSONObject response = new JSONObject();
    response.put("Message", "Success");
    return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
  }

  public static String generateOtp() {
    Random random = new Random();
    int min = 100000;
    int max = 999999;
    int randomNumber = random.nextInt(max - min + 1) + min;
    return String.valueOf(randomNumber);
  }

  /**
   * Used in controllers for validating the output and throwing exception.
   * 
   * @param <T>
   * @param op
   * @param message
   * @return
   */
  public <T> T validateIfEmpty(Optional<T> op, String message) {
    if (op.isPresent()) {
      return op.get();
    }
    throw new RuntimeException(message);
  }
}
