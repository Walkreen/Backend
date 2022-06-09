package capstone.walkreen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyResponse {

    private LocalDate completionDate;

    private Boolean missionA;

    private Boolean missionB;

    private Boolean missionC;

    private Boolean missionD;

    private Boolean missionE;

}
