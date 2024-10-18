/**
 * Copyright (c) 2023 Your Credex Technology. 
 * All rights reserved.
 */
package com.credex.hiring.portal.config;

public interface Endpoints {

  String CORS_ORIGIN = "https://campushiring.credextechnology.com/";
  String CAMPUS = "/campus";
  String BASE_PATH = "/";
  String CREATE = "/create";
  String DELETE = "/delete/{id}";

  // Campus Drive Endpoints
  String UPLOAD_JD = "/upload-jd/{driveCode}";
  String BEGIN_EXAM = "/begin-exam/{driveCode}";
  String END_EXAM = "/end-exam/{driveCode}";
  String EXAM_STATUS = "/get-exam-status/{collegeCode}";

  // Recruiters Endpoints
  String ADD_RECRUITER = "/add-recruiters";
  String GET_RECRUITER = "/get-recruiters/{id}";
  String DELETE_RECRUITER = "/delete-recruiter/{id}";

  // Candidates Endpoints
  String CANDIDATES = "/candidates";
  String CANDIDATES_GET_BY_COLLEGE = "/get-by-college/{code}";
  String CANDIDATES_GET_BY_USERID = "/get-by-userid/{id}";
  String CANDIDATES_UPDATE_REMARK1 = "/update-remark1/{candidateId}";
  String CANDIDATES_UPDATE_REMARK2 = "/update-remark2/{candidateId}";
  String CANDIDATES_UPDATE_REMARK3 = "/update-remark3/{candidateId}";
  String CANDIDATES_UPDATE_RECRUITER1 = "/update-recruiter1/{candidateId}";
  String CANDIDATES_UPDATE_RECRUITER2 = "/update-recruiter2/{candidateId}";
  String CANDIDATES_UPDATE_RECRUITER3 = "/update-recruiter3/{candidateId}";

}
