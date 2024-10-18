/**
 * Copyright (c) 2023 Your Credex Technology. 
 * All rights reserved.
 */
package com.credex.hiring.portal.utility;

import com.credex.hiring.portal.config.Endpoints;

public interface Constants extends Endpoints {

  // HTTP
  String APPLICATION_JSON = "application/json";

  // Parameters
  String DRIVE_CODE = "driveCode";
  String COLLEGE_CODE = "collegeCode";
  String ID = "id";
  String JOB_DESCRIPTION = "jobDescription";
  String JWT = "JWT";
  String GLOBAL = "global";
  String ACCESS_EVERYTHING = "accessEverything";
  String HEADER = "header";
  String CANDIDATE_ID = "candidateId";
  String CODE = "code";
  String STATUS = "status";
  String REMARK = "remark";
  String RECRUITER = "recruiter";

  // Path
  String BASE_PACKAGE = "com.credex.hiring.portal";
  String EMAIL_TEMPLATES_PATH = "/email-templates";
  String EXCEPTION_MESSAGES = "exception_messages";
}
