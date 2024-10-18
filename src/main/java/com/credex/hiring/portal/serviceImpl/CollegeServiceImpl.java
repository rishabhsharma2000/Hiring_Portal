package com.credex.hiring.portal.serviceImpl;

import com.credex.hiring.portal.entities.College;
import com.credex.hiring.portal.repository.CollegeRepo;
import com.credex.hiring.portal.service.CollegeService;
import com.credex.hiring.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollegeServiceImpl implements CollegeService {

  @Autowired
  private CollegeRepo collegeRepo;

  @Override
  public List<College> fetchColleges() {
    try {
      return collegeRepo.findAll();
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }
  }

  @Override
  public ResponseEntity<?> createColleges(College college) {
    try {
      collegeRepo.save(college);
      return Utility.responseCreate(HttpStatus.CREATED);
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }
  }

  @Override
  public ResponseEntity<?> deleteCollege(String id) {
    try {
      collegeRepo.deleteById(id);
      return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }
  }

  @Override
  public ResponseEntity<String> updateCollege(String id, College college) {
    try {
      Optional<College> opt = collegeRepo.findById(id);
      if (opt.isPresent()) {
        College collegeToUpdate = opt.get();
        Utility.updateFields(collegeToUpdate, college, College.class);
        collegeRepo.save(collegeToUpdate);
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<String>("paperId doesn't exist", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }
  }

  @Override
  public College fetchCollegeByCode(String code) {
    try {
      return new Utility().validateIfEmpty(collegeRepo.findById(code), "College Doesn't Exist");
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }
  }
}
