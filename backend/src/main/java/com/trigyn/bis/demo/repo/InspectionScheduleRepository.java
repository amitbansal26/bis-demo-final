package com.trigyn.bis.demo.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.trigyn.bis.demo.domain.*;
public interface InspectionScheduleRepository extends JpaRepository<InspectionSchedule, Long>{
  List<InspectionSchedule> findByRegion(String region);
}
