package capstone.walkreen.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class DailyMission extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Date completionDate;

    private Boolean missionA;

    private Boolean missionB;

    private Boolean missionC;

    private Boolean missionD;

    private Boolean missionE;

}
