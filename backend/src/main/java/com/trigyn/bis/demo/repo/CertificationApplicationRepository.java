package com.trigyn.bis.demo.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.trigyn.bis.demo.domain.CertificationApplication;
import com.trigyn.bis.demo.domain.AppUser;
public interface CertificationApplicationRepository extends JpaRepository<CertificationApplication, Long>{
  List<CertificationApplication> findByApplicant(AppUser user);
}