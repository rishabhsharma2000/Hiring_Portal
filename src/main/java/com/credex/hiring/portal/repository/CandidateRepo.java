package com.credex.hiring.portal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.credex.hiring.portal.entities.Candidates;

@Transactional
@Repository
public interface CandidateRepo extends JpaRepository<Candidates, String> {

  @Modifying
  @Query(value = "update candidates set quiz_score = ?1 , programming_score = ?2 , "
          + "overall_score = ?3 , is_evaluated = true where roll_number = ?4", nativeQuery = true)
  void updateCandidatesQuizScore(Long S1, Long S2, Long overallScore, String candidateId);

  @Query(value = "select * from candidates where college = ?1 and  \n"
          + "(  (status1 != \"REJ\" or status1 is null) and " + "(status2 != \"REJ\" or status2 is null) and "
          + "(status3 != \"REJ\" or status3 is null) )", nativeQuery = true)
  List<Candidates> getCandidateByCollegeCode(String code);

  @Modifying
  @Query(value = "update candidates set status1 = ?1, remark1 = ?2 where roll_number = ?3", nativeQuery = true)
  void updateRemark1(String status, String remark, String rollNumber);

  @Modifying
  @Query(value = "update candidates set status2 = ?1, remark2 = ?2 where roll_number = ?3", nativeQuery = true)
  void updateRemark2(String status, String remark, String rollNumber);

  @Modifying
  @Query(value = "update candidates set status3 = ?1, remark3 = ?2 where roll_number = ?3", nativeQuery = true)
  void updateRemark3(String status, String remark, String rollNumber);

  @Modifying
  @Query(value = "update candidates set recruiter1 = ?1 where roll_number = ?2", nativeQuery = true)
  void updateRecruiter1(Long recId, String rollNumber);

  @Modifying
  @Query(value = "update candidates set recruiter2 = ?1 where roll_number = ?2", nativeQuery = true)
  void updateRecruiter2(Long recId, String rollNumber);

  @Modifying
  @Query(value = "update candidates set recruiter3 = ?1 where roll_number = ?2", nativeQuery = true)
  void updateRecruiter3(Long recId, String rollNumber);

  @Query(value = "select * from candidates where user_id = ?1", nativeQuery = true)
  Optional<Candidates> getCandidateByUserId(Long userId);

  @Modifying
  @Query(value = "update candidates set exam_submitted = true where roll_number = ?1", nativeQuery = true)
  void updateExamSubmitionStatus(String rollNumber);
  Optional<Candidates> findByRollNumber(String rollNumber);

}
