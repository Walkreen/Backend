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
public class DayResponse {

    private Long userId;

    private DailyResponse dailyResponse;

    private List<MissionResponse> missions  = new ArrayList<>();

}
