package com.credex.hiring.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.credex.hiring.portal.entities.Examination;

@Repository
public interface ExamRepo extends JpaRepository<Examination, Long> {

  @Query(value = "select exam_id from examination order by RAND() LIMIT 1", nativeQuery = true)
  Long getRandomExamId();
}
