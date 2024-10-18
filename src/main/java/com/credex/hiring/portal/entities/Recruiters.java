package com.credex.hiring.portal.entities;

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
@Table(name = "recruiters")
@ToString
@Getter
@Setter
public class Recruiters {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long recruiterId;

  @ManyToOne
  @JoinColumn(name = "recruiter_user_id", nullable = false)
  private User recruiterUserId;

  @ManyToOne
  @JoinColumn(name = "recruiter_campus_id", nullable = false)
  private CampusDrive recruiterCampusId;
}
