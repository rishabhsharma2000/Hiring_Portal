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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "question_paper")
@Entity
@Getter
@Setter
@ToString
public class QuestionPaper {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long paperId;

  @ManyToOne
  @JoinColumn(name = "examId")
  private Examination examId;

  @Column(name = "question", unique = false, length = 1000)
  private String question;

  @Column(name = "choice1")
  private String choice1;

  @Column(name = "choice2")
  private String choice2;

  @Column(name = "choice3")
  private String choice3;

  @Column(name = "choice4")
  private String choice4;

  @Column(name = "answer", length = 1)
  private String answer;

  @Column(name = "ques_type")
  private String questionType;
}
