package com.credex.hiring.portal.serviceImpl;

import com.credex.hiring.portal.entities.Examination;
import com.credex.hiring.portal.repository.ExamRepo;
import com.credex.hiring.portal.service.ExamService;
import com.credex.hiring.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamRepo examRepo;

    @Override
    public List<Examination> fetchAllExam() {
        try {
            return examRepo.findAll();
        } catch(Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<String> saveExam(Examination exam) {
        try {
            examRepo.save(exam);
            return Utility.responseCreate(HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteExam(Long id) {
        try {
            examRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
