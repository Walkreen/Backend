package capstone.walkreen.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Mission {

    @Id
    @GeneratedValue
    @Column(name = "mission_id")
    private Long id;

    private String title;

    private String contents;

    private Long reward;

    @Setter
    private Long people;

    private LocalDate startTime;

    private LocalDate endTime;
}