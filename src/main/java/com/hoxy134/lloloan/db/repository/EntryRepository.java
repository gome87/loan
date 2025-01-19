package com.hoxy134.lloloan.db.repository;

import com.hoxy134.lloloan.db.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {

    Optional<Entry> findByApplicationId(Long ApplicationId);

}
