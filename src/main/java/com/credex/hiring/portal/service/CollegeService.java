package com.credex.hiring.portal.service;

import com.credex.hiring.portal.entities.College;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CollegeService {

    public List<College> fetchColleges();
    public ResponseEntity createColleges(College college);
    public ResponseEntity deleteCollege(String id);
    public ResponseEntity updateCollege(String id, College college);
    public College fetchCollegeByCode(@PathVariable("code") String code);
}
