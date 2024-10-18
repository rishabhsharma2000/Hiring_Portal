package com.credex.hiring.portal.controller;

import com.credex.hiring.portal.config.Endpoints;
import com.credex.hiring.portal.entities.Evaluation;
import com.credex.hiring.portal.repository.CandidateRepo;
import com.credex.hiring.portal.repository.EvaluationRepo;
import com.credex.hiring.portal.service.ExamService;
import com.credex.hiring.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/evaluate")
@CrossOrigin(origins = Endpoints.CORS_ORIGIN)
public class EvaluationController {

  @Autowired
  private EvaluationRepo evaluationRepo;

  @Autowired
  private CandidateRepo candidateRepo;


  @Autowired
  private ExamService examservice;

  @PostMapping(path = "/insert", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> insertAnswers(@RequestBody List<Evaluation> evaluations) {
    try {
      evaluationRepo.saveAll(evaluations);
      candidateRepo.updateExamSubmitionStatus(evaluations.get(0).getCandidateId().getRollNumber());
      return Utility.responseCreate(HttpStatus.CREATED);
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }
  }



  @PostMapping(path = "/updateAnswer")
  public ResponseEntity<?> updateAnswer(@RequestBody List<Evaluation> evaluations) {
    try {
      for (Evaluation evaluation : evaluations) {
        String evaluationRollNumber = evaluations.get(0).getCandidateId().getRollNumber();
//      String questionId = evaluation.getQuestionId().getPaperId();
        String evaluationSubmittedAnswer = evaluation.getSubmittedAnswer();
        // Update the answer for the current evaluation
        evaluationRepo.updateAnswerByQuestionId(evaluationSubmittedAnswer, evaluationRollNumber, evaluation.getQuestionId());
      }
      candidateRepo.updateExamSubmitionStatus(evaluations.get(0).getCandidateId().getRollNumber());
      return Utility.responseCreate(HttpStatus.CREATED);
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }
  }


//  }


  @GetMapping(path = "/get-answer/{id}")
  public List<Evaluation> getAllAnswers(@PathVariable("id") String id) {
    try {
      return evaluationRepo.getAnswerSheetByCandidateId(id);
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }
  }

  // Returns A candidate's Programming Question to the invigilator
  @GetMapping(path = "/answer-sheet/{id}")
  public List<Evaluation> getCandidatesAnswerSheet(@PathVariable("id") String id) {
    try {
      return evaluationRepo.getProgrammingQuestionForCandidate(id);
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }
  }


  @PostMapping(path = "/program-evaluate", produces = "application/json")
  public ResponseEntity<?> evaluateCandidateAndUpdateFinalScore(@RequestParam("score") Long score,
                                                                @RequestParam("candidateId") String candidateId) {
    try {
      List<Evaluation> answerSheet = evaluationRepo.getMcqQuestionForCandidate(candidateId);
      Long quizScore = 0L;

      for (int i = 0; i < answerSheet.size(); i++) {
        Evaluation obj = answerSheet.get(i);
        if (obj.getSubmittedAnswer().equals(obj.getQuestionId().getAnswer())) {
          quizScore++;
        }
      }
      Long overallScore = (long) Math.ceil((quizScore / 2.0) + (score / 2.0));
      candidateRepo.updateCandidatesQuizScore(quizScore, score, overallScore, candidateId);
      return Utility.responseSuccess();
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }
  }

}
