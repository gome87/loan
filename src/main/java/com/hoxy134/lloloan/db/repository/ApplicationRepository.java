package com.hoxy134.lloloan.db.repository;

import com.hoxy134.lloloan.db.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

}
