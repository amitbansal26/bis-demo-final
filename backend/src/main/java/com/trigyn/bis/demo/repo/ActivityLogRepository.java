package com.trigyn.bis.demo.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import com.trigyn.bis.demo.domain.ActivityLog;
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long>{}