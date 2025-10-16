package com.trigyn.bis.demo.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.trigyn.bis.demo.domain.AppUser;
public interface AppUserRepository extends JpaRepository<AppUser, Long>{
  Optional<AppUser> findByMobile(String mobile);
  Optional<AppUser> findByEmail(String email);
}