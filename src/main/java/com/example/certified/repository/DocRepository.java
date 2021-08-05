package com.example.certified.repository;

import com.example.certified.model.Docs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DocRepository extends JpaRepository<Docs, Long> {
    List<Docs> findByUser(String user);
    Long deleteByUser(String user);
}
