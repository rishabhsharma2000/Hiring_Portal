package com.credex.hiring.portal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class EmailData {
  private String to;
  private String subject;
  private Map<String, Object> emailData;
}
