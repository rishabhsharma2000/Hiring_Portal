package com.credex.hiring.portal.service;

import com.credex.hiring.portal.entities.Examination;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExamService {

    public List<Examination> fetchAllExam();
    public ResponseEntity<String> saveExam(Examination exam);
    public ResponseEntity<String> deleteExam(Long id);
}
