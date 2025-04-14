package repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import user.User;

public interface UserRepo extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
}
