package com.credex.hiring.portal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "candidates")
@Getter
@Setter
@ToString
public class Candidates {

  @Id
  @Column(unique = true, nullable = false)
  private String rollNumber;

  @Column(nullable = false)
  private String name;

  @Column(unique = true, length = 10)
  private String mobileNumber;

  @Column(nullable = false)
  private String dateOfBirth;

  @Column(unique = true, nullable = false)
  private String emailId;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  private double tenthMarks;

  @Column(nullable = false)
  private String tenthBoard;

  @Column(nullable = false)
  private String tenthSchoolName;

  @Column(nullable = false)
  private double twelfthMarks;
  @Column(nullable = false)
  private String twelfthBoard;
  @Column(nullable = false)
  private String twelfthSchoolName;

  @Lob
  @Column(name = "resume_file")
  private byte[] resumeFile;

  @Lob
  @Column(name = "image_photo")
  private byte[] imagePhoto;

  @Column(nullable = false)
  private String course;
  @Column(nullable = false)
  private double cgpa;

  @ManyToOne
  @JoinColumn(name = "college", nullable = false)
  private College collegeName;

  @Column(nullable = false)
  private String gender;

  private Boolean isRegistered = false;

  @OneToOne
  @JoinColumn(name = "user_id", unique = true)
  private User userId;

  private Integer quizScore;
  private Integer programmingScore;
  private Integer overallScore;

  private Boolean isEvaluated = false;
  private Boolean examSubmitted = false;

  // Interview Process
  private String status1;
  private String status2;
  private String status3;

  // Recruiters
  @ManyToOne
  @JoinColumn(name = "recruiter1")
  private Recruiters recruiter1;

  @ManyToOne
  @JoinColumn(name = "recruiter2")
  private Recruiters recruiter2;

  @ManyToOne
  @JoinColumn(name = "recruiter3")
  private Recruiters recruiter3;

  private String remark1;
  private String remark2;
  private String remark3;

  @Column(name = "reopen_exam")
  private Boolean reopenExam=false;

  @Column(name = "isexamstarted")
  private Boolean isexamstarted=false;
}
