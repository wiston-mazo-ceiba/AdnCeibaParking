package co.ceiba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import co.ceiba.entity.ApplicationUser;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
	ApplicationUser findByUsername(String username);
}
