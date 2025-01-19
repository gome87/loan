package com.hoxy134.lloloan.db.repository;

import com.hoxy134.lloloan.db.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {

    Optional<Balance> findByApplicationId(Long applicationId);

}
