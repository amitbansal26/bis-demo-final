package com.trigyn.bis.demo.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import com.trigyn.bis.demo.domain.DocumentRef;
public interface DocumentRefRepository extends JpaRepository<DocumentRef, Long>{}