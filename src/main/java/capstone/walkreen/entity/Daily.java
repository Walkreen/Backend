package capstone.walkreen.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Daily extends BaseEntity {

    @ManyToOne
    private User user;

    private LocalDate completionDate;

    private Boolean missionA;

    private Boolean missionB;

    private Boolean missionC;

    private Boolean missionD;

    private Boolean missionE;

}
