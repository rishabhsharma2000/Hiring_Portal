package com.credex.hiring.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.credex.hiring.portal.entities.CampusDrive;

@Repository
@Transactional
public interface CampusRepo extends JpaRepository<CampusDrive, Long> {

  @Modifying
  @Query(value = "update campus_drive set is_exam_started = true where drive_code = ?1", nativeQuery = true)
  void startExam(Long driveCode);

  @Modifying
  @Query(value = "update campus_drive set is_exam_started = false where drive_code = ?1", nativeQuery = true)
  void endExam(Long driveCode);

  @Query(value = "select is_exam_started from campus_drive where college_id = ?1", nativeQuery = true)
  Boolean getExamStatusByCollegeCode(String collegeCode);
}
