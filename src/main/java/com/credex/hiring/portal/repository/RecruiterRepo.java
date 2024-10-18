package com.credex.hiring.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.credex.hiring.portal.entities.Recruiters;

@Repository
public interface RecruiterRepo extends JpaRepository<Recruiters, Long> {

  @Query(value = "select * from recruiters where recruiter_campus_id = ?1", nativeQuery = true)
  List<Recruiters> getRecruiterByDriveCode(Long id);
}
