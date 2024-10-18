package com.credex.hiring.portal.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PasswordChangedData {
  String oldPassword = "";
  String newPassword = "";
  String email = "";
}
