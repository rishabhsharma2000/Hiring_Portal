package com.credex.hiring.portal.serviceImpl;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.credex.hiring.portal.entities.Candidates;
import com.credex.hiring.portal.exception.UsersException;
import com.credex.hiring.portal.repository.CandidateRepo;
import com.credex.hiring.portal.service.IUserService;
import com.credex.hiring.portal.utility.ResourceProperties;

@Service
public class UserServiceImpl implements IUserService {

  @Autowired
  CandidateRepo candidateRepo;

  @Override
  public Candidates upload(Candidates candidates, MultipartFile filePhoto, MultipartFile fileResume)
      throws UsersException, IOException {

    if (Objects.isNull(candidates)) {
      throw new UsersException(ResourceProperties.getMessage(ResourceProperties.EXCEPTION_02));
    }

    if (Objects.nonNull(filePhoto)) {
      String extensionPhoto = FilenameUtils.getExtension(filePhoto.getOriginalFilename());

      if (extensionPhoto.equalsIgnoreCase("jpg") || extensionPhoto.equalsIgnoreCase("png")
          || extensionPhoto.equalsIgnoreCase("jpeg")) {
        candidates.setImagePhoto(filePhoto.getBytes());

      } else {
        throw new UsersException(ResourceProperties.getMessage(ResourceProperties.EXCEPTION_01));
      }
    } else {
      candidates.setImagePhoto(null);
    }

    if (Objects.nonNull(fileResume)) {
      String extensionResume = FilenameUtils.getExtension(fileResume.getOriginalFilename());
      if (extensionResume.equalsIgnoreCase("pdf")) {
        candidates.setResumeFile(fileResume.getBytes());
      } else {
        throw new UsersException("Resume should be in PDF format");
      }
    } else {
      candidates.setResumeFile(null);
    }
    return candidateRepo.save(candidates);
  }

  public boolean isCandidateEligibleForReopeningExam(String rollNumber) {
    Optional<Candidates> candidate = candidateRepo.findByRollNumber(rollNumber);


    if (candidate.isPresent()) {
      Candidates candidates = candidate.get();
      candidates.setExamSubmitted(false);
      System.out.println(candidates.getExamSubmitted());
      candidates.setIsEvaluated(false);
      System.out.println(candidates.getIsEvaluated());

      candidates.setOverallScore(null);
      System.out.println(candidates.getOverallScore());

      candidates.setProgrammingScore(null);
      System.out.println(candidates.getProgrammingScore());

      candidates.setQuizScore(null);
      System.out.println(candidates.getQuizScore());

      candidates.setReopenExam(true);

      // Update the candidate entity
      candidateRepo.save(candidates);
      System.out.println(candidate);


      return true; // Return true if the candidate is eligible
    }

    return false; // Return false if the candidate is not eligible
  }
  public boolean isExamStarted(String rollNumber) {
    Optional<Candidates> candidate = candidateRepo.findByRollNumber(rollNumber);

    if (candidate.isPresent()) {
      Candidates candidates = candidate.get();

      // Set isexamstarted to true
      candidates.setIsexamstarted(true);
      System.out.println(candidates.getIsexamstarted());

      // Update the candidate entity
      candidateRepo.save(candidates);
      System.out.println(candidate);

      return true; // Return true if the exam is started for the candidate
    }

    return false; // Return false if the candidate is not found
  }
  }