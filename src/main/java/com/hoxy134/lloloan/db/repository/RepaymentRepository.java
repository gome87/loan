package com.hoxy134.lloloan.db.repository;

import com.hoxy134.lloloan.db.entity.Repayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepaymentRepository extends JpaRepository<Repayment, Long> {

    List<Repayment> findAllByApplicationId(Long applicationId);

}
