package com.hoxy134.lloloan.db.repository;

import com.hoxy134.lloloan.db.entity.Counsel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CounselRepository extends JpaRepository<Counsel, Long> {

}
