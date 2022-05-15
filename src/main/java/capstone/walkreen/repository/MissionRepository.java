package capstone.walkreen.repository;

import capstone.walkreen.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {


}
