package capstone.walkreen.entity;


import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Setter
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
