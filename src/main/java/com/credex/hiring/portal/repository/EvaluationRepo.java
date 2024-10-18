package com.credex.hiring.portal.repository;

import java.util.List;

import com.credex.hiring.portal.entities.Candidates;
import com.credex.hiring.portal.entities.QuestionPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.credex.hiring.portal.entities.Evaluation;

@Repository
@Transactional
public interface EvaluationRepo extends JpaRepository<Evaluation, Long> {

  @Query(value = "select id, submitted_answer,e.exam_id, question_id, candidate_id  "
          + "from evaluation as e join question_paper as q on e.question_id = q.paper_id where candidate_id = "
          + "?1 and ques_type = \"MCQ\";", nativeQuery = true)
  List<Evaluation> getMcqQuestionForCandidate(String candidateId);

  @Query(value = "select id, submitted_answer,e.exam_id, question_id, candidate_id  "
          + "from evaluation as e join question_paper as q on e.question_id = q.paper_id where candidate_id = "
          + "?1 and ques_type = \"Programming\" ", nativeQuery = true)
  List<Evaluation> getProgrammingQuestionForCandidate(String candidateId);


  @Query(value = "select * from evaluation where candidate_id = ?1", nativeQuery = true)
  List<Evaluation> getAnswerSheetByCandidateId(String candidateId);

//  @Modifying
//  @Query(value = "update evaluation set submitted_answer = :submitted_answer where roll_Number = :roll_number and paper_id = :paper_id", nativeQuery = true)
//  int updateAnswerByPaperId(@Param("submitted_answer") String submittedAnswer, @Param("roll_number") String roll_number,@Param("paper_id") Long paperId);

  @Modifying
  @Query(value = "update evaluation set submitted_answer = :submitted_answer where candidate_id = :roll_number and question_id = :question_id", nativeQuery = true)
  int updateAnswerByQuestionId(@Param("submitted_answer") String submittedAnswer, @Param("roll_number") String roll_number, @Param("question_id") QuestionPaper question);


}
