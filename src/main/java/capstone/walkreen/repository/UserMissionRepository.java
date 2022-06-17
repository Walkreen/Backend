package capstone.walkreen.repository;

import capstone.walkreen.entity.Mission;
import capstone.walkreen.entity.User;
import capstone.walkreen.entity.UserMission;
import capstone.walkreen.enumerations.MissionStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserMissionRepository extends JpaRepository<UserMission, Long> {

    List<UserMission> findAllByUser(User user);

    List<UserMission> findAllByUserAndStatus(User user, MissionStatus status);

    List<UserMission> findAllByUserAndStatusAndCompletionDate(User user, MissionStatus status, LocalDate completionDate);
}
