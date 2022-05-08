package capstone.walkreen.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Mission extends BaseEntity {

    private String name;

    private String contents;

    private String reward;

    private String startTime;

    private String endTime;

    // private mission type
}
