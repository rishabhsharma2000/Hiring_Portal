package com.credex.hiring.portal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "evaluation")
@Getter
@Setter
@ToString
public class Evaluation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "exam_id")
  private Examination examId;

  @ManyToOne
  @JoinColumn(name = "question_id")
  private QuestionPaper questionId;

  @Column(nullable = false, length = 5000)
  private String submittedAnswer;

  @ManyToOne
  @JoinColumn(name = "candidate_id")
  private Candidates candidateId;
}
