package com.credex.hiring.portal.controller;

import com.credex.hiring.portal.config.Endpoints;
import com.credex.hiring.portal.entities.College;
import com.credex.hiring.portal.service.CollegeService;
import com.credex.hiring.portal.utility.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/college")
@CrossOrigin(origins = Endpoints.CORS_ORIGIN)
public class CollegeController implements Constants {

  @Autowired
  private CollegeService collegeService;

  @GetMapping(path = "/")
  public List<College> fetchColleges() {
    return collegeService.fetchColleges();
  }

  @GetMapping(path = "/{code}")
  public College fetchCollegeByCode(@PathVariable("code") String code) {
    return collegeService.fetchCollegeByCode(code);
  }

  @PostMapping(path = Endpoints.CREATE, consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> createColleges(@Valid @RequestBody College college) {
    return collegeService.createColleges(college);
  }

  @PutMapping(path = "/update/{id}", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> updateCollege(@PathVariable("id") String id, @Valid @RequestBody College college) {
    return collegeService.updateCollege(id, college);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deleteCollege(@PathVariable(ID) String id) {
    return collegeService.deleteCollege(id);
  }
}
