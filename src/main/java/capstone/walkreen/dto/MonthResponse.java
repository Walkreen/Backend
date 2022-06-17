package capstone.walkreen.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class MonthResponse {

    private Long userId;

    private String calendar;

    private List<MissionScoreResponse> missionScoreResponses = new ArrayList<>();

}
