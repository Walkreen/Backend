package capstone.walkreen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
public class SetMissionRequest {

    private String title;

    private String content;

    private Integer reward;

    private LocalDate startTime;

    private LocalDate endTime;
}
