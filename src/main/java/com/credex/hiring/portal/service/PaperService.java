package com.credex.hiring.portal.service;

import com.credex.hiring.portal.entities.QuestionPaper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

public interface PaperService {
    public List<QuestionPaper> fetchAllPaper();
    public ResponseEntity deletePaper(Long id);
    public ResponseEntity updatePaper(Long id, QuestionPaper paper);
    public ResponseEntity<String> insert(List<QuestionPaper> paper);
    public ResponseEntity uploadFile(MultipartFile file, Long examId);
    public List<QuestionPaper> fetchPaperById(Long id);
    public List<QuestionPaper> startExam();
}
