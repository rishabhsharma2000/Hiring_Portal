package com.credex.hiring.portal.repository;

import com.credex.hiring.portal.entities.QuestionPaper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.credex.hiring.portal.entities.QuestionPaper;

import java.util.List;

@Repository
@Transactional
public interface PaperRepo extends JpaRepository<QuestionPaper, Long> {

    @Query(value = "select * from question_paper where exam_id = ?1", nativeQuery = true)
    List<QuestionPaper> getRandomExam(Long examId);
    
    @Modifying
    @Query(value = "delete from question_paper where exam_id = ?1", nativeQuery = true)
    void deleteQuestionByExamId(Long id);
}
