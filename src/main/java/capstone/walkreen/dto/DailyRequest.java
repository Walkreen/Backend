package capstone.walkreen.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Getter
public class DailyRequest {

    private LocalDate completionDate;

    private Character mission;

}
