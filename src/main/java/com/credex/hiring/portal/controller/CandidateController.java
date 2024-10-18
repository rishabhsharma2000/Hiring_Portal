/**
 * Copyright (c) 2023 Your Credex Technology. 
 * All rights reserved.
 */
package com.credex.hiring.portal.controller;

import com.credex.hiring.portal.config.Endpoints;
import com.credex.hiring.portal.entities.Candidates;
import com.credex.hiring.portal.exception.UserNotFoundException;
import com.credex.hiring.portal.exception.UsersException;
import com.credex.hiring.portal.repository.CandidateRepo;
import com.credex.hiring.portal.service.IUserService;
import com.credex.hiring.portal.utility.Constants;
import com.credex.hiring.portal.utility.ResourceProperties;
import com.credex.hiring.portal.utility.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = Endpoints.CANDIDATES)
@CrossOrigin(origins = Endpoints.CORS_ORIGIN)
public class CandidateController implements Constants {

  @Autowired
  private CandidateRepo candidateRepo;

  @Autowired
  IUserService iUserService;

  @GetMapping(path = BASE_PATH)
  public List<Candidates> getAllCandidates() {
    return candidateRepo.findAll();
  }

  @GetMapping(path = "/{id}")
  public Candidates getCandidateById(@PathVariable(ID) String  id) {
    return new Utility().validateIfEmpty(candidateRepo.findById(id),
            ResourceProperties.getMessage(ResourceProperties.EXCEPTION_03));
  }

  @PostMapping(path = CREATE, consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
  public ResponseEntity<?> createCandidate(@Valid @RequestBody Candidates candidate) {
    try {
      candidate.setIsRegistered(true);
      candidateRepo.save(candidate);
      return Utility.responseCreate(HttpStatus.CREATED);
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }
  }

  @DeleteMapping(path = DELETE)
  public ResponseEntity<?> deleteCandidate(@PathVariable(ID) String  id) {
    candidateRepo.deleteById(id);
    return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
  }

  @GetMapping(path = CANDIDATES_GET_BY_COLLEGE)
  public List<Candidates> getCandidatesByCollegeCode(@PathVariable(CODE) String code) {
    try {
      return candidateRepo.getCandidateByCollegeCode(code);
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }
  }

  @GetMapping(path = CANDIDATES_GET_BY_USERID)
  public Candidates getCandidateByUserId(@PathVariable(ID) Long  id) throws UserNotFoundException {
    return new Utility().validateIfEmpty(candidateRepo.getCandidateByUserId(id),
            ResourceProperties.getMessage(ResourceProperties.EXCEPTION_04));
  }

  @PostMapping(path = CANDIDATES_UPDATE_REMARK1)
  public ResponseEntity<?> updateRemark1(@PathVariable(CANDIDATE_ID) String id, @RequestParam(STATUS) String status,
                                         @RequestParam(REMARK) String remark) {
    try {
      candidateRepo.updateRemark1(status, remark, id);
      return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }
  }

  @PostMapping(path = CANDIDATES_UPDATE_REMARK2)
  public ResponseEntity<?> updateRemark2(@PathVariable(CANDIDATE_ID) String id, @RequestParam(STATUS) String status,
                                         @RequestParam(REMARK) String remark) {
    try {
      candidateRepo.updateRemark2(status, remark, id);
      return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }
  }

  @PostMapping(path = CANDIDATES_UPDATE_REMARK3)
  public ResponseEntity<?> updateRemark3(@PathVariable(CANDIDATE_ID) String id, @RequestParam(STATUS) String status,
                                         @RequestParam(REMARK) String remark) {
    try {
      candidateRepo.updateRemark3(status, remark, id);
      return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }
  }

  @PostMapping(path = CANDIDATES_UPDATE_RECRUITER1)
  public ResponseEntity<?> updateRecruiter1(@PathVariable(CANDIDATE_ID) String candidateId,
                                            @RequestParam(RECRUITER) Long recId) {
    try {
      candidateRepo.updateRecruiter1(recId, candidateId);
      return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }


  }


  @PostMapping(path = CANDIDATES_UPDATE_RECRUITER2)
  public ResponseEntity<?> updateRecruiter2(@PathVariable(CANDIDATE_ID) String candidateId,
                                            @RequestParam(RECRUITER) Long recId) {
    try {
      candidateRepo.updateRecruiter2(recId, candidateId);
      return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }
  }

  @PostMapping(path = CANDIDATES_UPDATE_RECRUITER3)
  public ResponseEntity<?> updateRecruiter3(@PathVariable(CANDIDATE_ID) String candidateId,
                                            @RequestParam(RECRUITER) Long recId) {
    try {
      candidateRepo.updateRecruiter3(recId, candidateId);
      return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }
  }

  @PostMapping("/uploadResume")
  @ApiOperation(value = "Upload a candidate's resume")
  @ApiImplicitParam(name = "file", value = "Resume file to upload", dataType = "file", paramType = "form")
  public ResponseEntity<String> resumeUpload(@RequestParam("rollNumber") String  rollNumber, @RequestPart("file") MultipartFile file)
          throws UsersException, IOException {
    Optional<Candidates> optionalCandidate = candidateRepo.findById(rollNumber);

    if (!optionalCandidate.isPresent()) {
      throw new UsersException("Candidate with roll number " + rollNumber + " not found.");
    }
    Candidates candidate = optionalCandidate.get();

    // Convert the uploaded file to a byte array
    byte[] resumeBytes;
    InputStream inputStream = file.getInputStream();
    try {
      int fileSize = (int) file.getSize();
      resumeBytes = new byte[fileSize];
      int bytesRead = inputStream.read(resumeBytes);
      if (bytesRead != fileSize) {
        throw new IOException("Error reading the full file.");
      }
    } catch (Exception e) {
      throw new RuntimeException("Error reading the uploaded file.");
    } finally {
      inputStream.close();
    }
    candidate.setResumeFile(resumeBytes);
    candidateRepo.save(candidate);
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("Resume Uploaded Successfully !");


  }

  @GetMapping("/fetchResume")
  @ApiOperation(value = "Fetch a candidate's resume")
  @ApiImplicitParam(name = "rollNumber", value = "Roll number of the candidate", dataType = "long", paramType = "query")
  public ResponseEntity<ByteArrayResource> fetchResume(@RequestParam("rollNumber") String rollNumber) {
    try {
      // Find the candidate by roll number
      Optional<Candidates> optionalCandidate = candidateRepo.findById(rollNumber);
      if (optionalCandidate.isPresent()) {
        Candidates candidate = optionalCandidate.get();
        // Check if the candidate has a resume
        byte[] resumeBytes = candidate.getResumeFile();
        if (resumeBytes != null) {
          // Create a ByteArrayResource from the resume bytes
          ByteArrayResource resource = new ByteArrayResource(resumeBytes);
          return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(resource);
        } else {
          return ResponseEntity.notFound().build(); // No resume found for the candidate
        }
      } else {
        return ResponseEntity.notFound().build(); // Candidate not found
      }
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }
  }
  @PostMapping("/reopen-exam/{rollNumber}")
  public ResponseEntity<String> reopenExam(@PathVariable String rollNumber) {
    boolean success = iUserService.isCandidateEligibleForReopeningExam(rollNumber);

    if (success) {
      return ResponseEntity.ok("Exam reopened successfully for candidate with roll number: " + rollNumber);
    } else {
      return ResponseEntity.badRequest().body("Failed to reopen the exam for candidate with roll number: " + rollNumber);
    }
  }
  @PostMapping("/start")
  public ResponseEntity<String> startExam(@RequestParam("rollNumber") String rollNumber) {
    boolean examStarted =iUserService.isExamStarted(rollNumber);

    if (examStarted) {
      return new ResponseEntity<>("Exam started for candidate with rollNumber: " + rollNumber, HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Candidate with rollNumber " + rollNumber + " not found", HttpStatus.NOT_FOUND);
    }
  }
}



