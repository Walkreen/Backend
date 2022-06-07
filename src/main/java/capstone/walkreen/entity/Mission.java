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

    private String title;

    private String contents;

    private Long reward;
    
    // 이거 시간 자료형 생각해야함
    private String startTime;

    private String endTime;

    //private mission type;
}
