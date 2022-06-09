package capstone.walkreen.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageMissionResponse {

    private Long userId;

    private List<MissionResponse> missions  = new ArrayList<>();
}
