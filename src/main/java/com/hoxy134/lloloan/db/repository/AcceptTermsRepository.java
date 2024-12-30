package com.hoxy134.lloloan.db.repository;

import com.hoxy134.lloloan.db.entity.AcceptTerms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcceptTermsRepository extends JpaRepository<AcceptTerms, Long> {

}
