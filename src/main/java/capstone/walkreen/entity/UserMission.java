package capstone.walkreen.entity;

import capstone.walkreen.controller.MissionController;
import capstone.walkreen.enumerations.MissionStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class UserMission {

    @Id
    @GeneratedValue
    @Column(name = "user_mission_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @Setter
    private MissionStatus status;

    @Setter
    private LocalDate completionDate;

    @Builder
    public UserMission(User user, Mission mission, MissionStatus status) {
        this.user = user;
        this.mission = mission;
        this.status = status;
    }
}