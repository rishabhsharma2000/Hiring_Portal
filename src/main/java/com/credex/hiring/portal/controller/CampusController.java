/**
 * Copyright (c) 2023 Your Credex Technology. 
 * All rights reserved.
 */
package com.credex.hiring.portal.controller;

import com.credex.hiring.portal.config.Endpoints;
import com.credex.hiring.portal.entities.CampusDrive;
import com.credex.hiring.portal.entities.Recruiters;
import com.credex.hiring.portal.repository.RecruiterRepo;
import com.credex.hiring.portal.service.CampusService;
import com.credex.hiring.portal.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = Endpoints.CAMPUS)
@CrossOrigin(origins = Endpoints.CORS_ORIGIN)
public class CampusController implements Constants {

  @Autowired
  private CampusService campusService;

  @Autowired
  private RecruiterRepo recruiterRepo;

  @GetMapping(path = BASE_PATH)
  public List<CampusDrive> listAllCampus() {
    return campusService.listAllCampus();
  }

  @PostMapping(path = CREATE, consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
  public ResponseEntity<?> createCampus(@Valid @RequestBody CampusDrive drive) {
    return campusService.createCampus(drive);
  }

  @PostMapping(path = UPLOAD_JD, produces = APPLICATION_JSON)
  public ResponseEntity<?> uploadJD(@RequestParam(JOB_DESCRIPTION) MultipartFile file,
                                    @PathVariable(DRIVE_CODE) Long driveCode, @RequestParam("recipientName") String recipientName) {
    return campusService.uploadJD(file, driveCode, recipientName);
  }

  @GetMapping(path = BEGIN_EXAM)
  public void beginExam(@PathVariable(DRIVE_CODE) Long driveCode) {
    campusService.beginExam(driveCode);
  }

  @GetMapping(path = END_EXAM)
  public void endExam(@PathVariable(DRIVE_CODE) Long driveCode) {
    campusService.endExam(driveCode);
  }

  @GetMapping(path = EXAM_STATUS)
  public Boolean getExamStatusByCollegeCode(@PathVariable(COLLEGE_CODE) String code) {
    return campusService.getExamStatusFromCollegeCode(code);
  }

  @PostMapping(path = ADD_RECRUITER, consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
  public ResponseEntity<?> addRecruiterToCampusDrive(@Valid @RequestBody List<Recruiters> rec) {
    return campusService.addRecruiterToCampusDrive(rec);
  }

  @GetMapping(path = GET_RECRUITER)
  public List<Recruiters> fetchRecruiterByCampusCode(@PathVariable(ID) Long id) {
    return recruiterRepo.getRecruiterByDriveCode(id);
  }

  @DeleteMapping(path = DELETE_RECRUITER)
  public ResponseEntity<?> deleteRecruiterFromCampusDrive(@PathVariable(ID) Long recId) {
    recruiterRepo.deleteById(recId);
    return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
  }
}
