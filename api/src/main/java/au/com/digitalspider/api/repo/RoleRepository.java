package au.com.digitalspider.api.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import au.com.digitalspider.api.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findOneByNameIgnoreCase(String name);

	List<Role> findByNameContainingIgnoreCase(String name);
}
