package com.example.certified.repository;

import com.example.certified.model.Docs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DocRepository extends JpaRepository<Docs, Long> {

}
