package com.credex.hiring.portal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {

  @Id
  @Column(unique = true, nullable = false)
  String roleId;
  @Column(unique = true, nullable = false)
  String description;
}
