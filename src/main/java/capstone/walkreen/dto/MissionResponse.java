package capstone.walkreen.dto;

import capstone.walkreen.enumerations.MissionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionResponse {

    private Long missionId;

    private MissionStatus status;

    private String title;

    private String contents;

    private Long reward;

    private Long people;

    private LocalDate startTime;

    private LocalDate endTime;
}
