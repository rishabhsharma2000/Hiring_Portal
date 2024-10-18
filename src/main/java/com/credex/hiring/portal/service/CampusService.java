package com.credex.hiring.portal.service;

import com.credex.hiring.portal.entities.CampusDrive;
import com.credex.hiring.portal.entities.Recruiters;
import org.json.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

public interface CampusService {
    public List<CampusDrive> listAllCampus();
    public ResponseEntity createCampus(CampusDrive drive);

    void beginExam(Long driveCode);

    void endExam(Long driveCode);

    Boolean getExamStatusFromCollegeCode(String code);

    public ResponseEntity<?> uploadJD(MultipartFile file, Long driveCode, String recipientName);

    ResponseEntity addRecruiterToCampusDrive(List<Recruiters> rec);
}
