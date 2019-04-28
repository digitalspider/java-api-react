package au.com.digitalspider.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import au.com.digitalspider.api.model.User;

@Repository
@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends JpaRepository<User, Long> {

	User findOneByUsernameIgnoreCase(String username);

	User findOneByEmailIgnoreCase(String email);
}
