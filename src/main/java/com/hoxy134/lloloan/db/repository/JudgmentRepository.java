package com.hoxy134.lloloan.db.repository;

import com.hoxy134.lloloan.db.entity.Judgment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JudgmentRepository extends JpaRepository<Judgment, Long> {

    Optional<Judgment> findByApplicationId(Long applicationId);

}
