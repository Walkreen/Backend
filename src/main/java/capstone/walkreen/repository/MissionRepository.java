package capstone.walkreen.repository;

import capstone.walkreen.entity.Daily;
import capstone.walkreen.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {

    List<Mission> findAllByStartTimeIsBeforeAndEndTimeIsAfter(LocalDate today1, LocalDate today2);

    List<Mission> findAllByIdIsNotInAndStartTimeIsBeforeAndEndTimeIsAfter(List<Long> MissionIds, LocalDate today1, LocalDate today2);

    List<Mission> findAllByIdIsNotIn(List<Long> MissionIds);
}
