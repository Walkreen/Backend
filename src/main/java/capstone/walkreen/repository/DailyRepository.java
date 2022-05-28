package capstone.walkreen.repository;

import capstone.walkreen.entity.Daily;
import capstone.walkreen.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyRepository extends JpaRepository<Daily, Long> {

    List<Daily> findAllByUser(User user);

    List<Daily> findAllByUserAndCompletionDateBetween(User user, LocalDateTime start, LocalDateTime end);
}
