package capstone.walkreen.repository;

import capstone.walkreen.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

    Optional<User> findUserByEmail(String email);

    Boolean existsByNickname(String nickname);
}
