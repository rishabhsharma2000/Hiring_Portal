package com.credex.hiring.portal.controller;

import com.credex.hiring.portal.config.Endpoints;
import com.credex.hiring.portal.entities.QuestionPaper;
import com.credex.hiring.portal.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/question-paper")
@CrossOrigin(origins = Endpoints.CORS_ORIGIN)
public class PaperController {

  @Autowired
  private PaperService paperService;

  @GetMapping(path = Endpoints.BASE_PATH)
  public List<QuestionPaper> getPaperService() {
    return paperService.fetchAllPaper();
  }

  @GetMapping(path = "/{id}")
  public List<QuestionPaper> getPaperServiceById(@PathVariable("id") Long id) {
    return paperService.fetchPaperById(id);
  }

  @PostMapping(path = "/insert", consumes = "application/json", produces = "application/json")
  public ResponseEntity<String> insertBulk(@Valid @RequestBody List<QuestionPaper> paper) {
    return paperService.insert(paper);
  }

  @PostMapping(path = "/upload-file/{id}")
  public ResponseEntity<?> uploadFile(@RequestParam("image") MultipartFile file, @PathVariable("id") Long examId) {
    return paperService.uploadFile(file, examId);
  }

  @PutMapping(path = "/update/{id}", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> updatePaper(@PathVariable("id") Long id, @Valid @RequestBody QuestionPaper paper) {
    return paperService.updatePaper(id, paper);
  }

  @DeleteMapping(path = "/delete/{id}")
  public ResponseEntity<?> deletePaper(@PathVariable("id") Long id) {
    return paperService.deletePaper(id);
  }

  @GetMapping(path = "/start-exam")
  public List<QuestionPaper> startExam() {
    return paperService.startExam();
  }
}
