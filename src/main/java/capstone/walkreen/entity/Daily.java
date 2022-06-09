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
public class Daily {

    @Id
    @GeneratedValue
    @Column(name = "daily_id")
    private Long id;

    @ManyToOne
    private User user;

    private LocalDate completionDate;

    private Boolean missionA;

    private Boolean missionB;

    private Boolean missionC;

    private Boolean missionD;

    private Boolean missionE;

    public Daily(User user, LocalDate completionDate) {
        this.user = user;
        this.completionDate = completionDate;
        this.missionA = false;
        this.missionB = false;
        this.missionC = false;
        this.missionD = false;
        this.missionE = false;
    }
}
