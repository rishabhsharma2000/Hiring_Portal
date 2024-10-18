package com.credex.hiring.portal.repository;

import com.credex.hiring.portal.entities.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeRepo extends JpaRepository<College, String> {
}
