package com.credex.hiring.portal.controller;

import com.credex.hiring.portal.config.Endpoints;
import com.credex.hiring.portal.entities.Examination;
import com.credex.hiring.portal.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/exam")
@CrossOrigin(origins = Endpoints.CORS_ORIGIN)
public class ExamController {

  @Autowired
  private ExamService examService;

  @GetMapping("/")
  public List<Examination> fetchAllExam() {
    return examService.fetchAllExam();
  }

  @PostMapping(path = Endpoints.CREATE, consumes = "application/json", produces = "application/json")
  public ResponseEntity<String> saveExam(@Valid @RequestBody Examination exam) {
    return examService.saveExam(exam);
  }

  @DeleteMapping(path = "/delete/{id}")
  public ResponseEntity<String> deleteExam(@Valid @PathVariable("id") Long id) {
    return examService.deleteExam(id);
  }
}
