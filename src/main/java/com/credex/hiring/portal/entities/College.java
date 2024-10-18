package com.credex.hiring.portal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "college")
@Getter
@Setter
public class College {

  @Id
  @Column(unique = true, nullable = false)
  private String collegeCode;

  @Column(name = "collegeName", unique = true, nullable = false)
  private String collegeName;

  @Column(name = "location", nullable = false)
  private String location;

  @Column(name = "poc", nullable = false)
  private String poc; // Person Of Contact

  @Column(name = "contact", unique = true, nullable = false)
  private String contact;

  @Column(name = "email", unique = true, nullable = false)
  private String email;

  private Long totalStudents;
}
