package com.credex.hiring.portal.entities;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "campus_drive")
@Getter
@Setter
public class CampusDrive {

  @Id
  @Column(nullable = false, unique = true)
  private Long driveCode;

  @ManyToOne
  @JoinColumn(name = "college_id", nullable = false)
  private College collegeId; // POC and their info | College Location

  @ManyToOne
  @JoinColumn(name = "invigilator_id")
  private User invigilator; // One user will be available for

  private Integer noOfCandidates;
  private Integer noOfSelectedCandidates;

  @Column(nullable = false)
  private String dateOfCampus;

  @Column(nullable = false)
  private String examStartTime;

  @Column(nullable = false)
  private String examEndTime;

  private Boolean isExamStarted = false;

  private String mode;
}
