package com.credex.hiring.portal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "examination")
@Getter
@Setter
public class Examination {

  @Id
  @Column(unique = true, nullable = false)
  private Long examId;

  @Column(nullable = false)
  private String description;
}
