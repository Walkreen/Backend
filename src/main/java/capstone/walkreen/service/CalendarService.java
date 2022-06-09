package capstone.walkreen.service;

import capstone.walkreen.dto.DailyResponse;
import capstone.walkreen.dto.StringResponse;
import capstone.walkreen.repository.MissionRepository;
import capstone.walkreen.repository.UserMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private UserMissionRepository userMissionRepository;
    private MissionRepository missionRepository;

    public DailyResponse getMonthCalendar(LocalDate month, HttpServletRequest httpServletRequest) {


        return new DailyResponse();
    }

    public DailyResponse getDayCalendar(LocalDate day, HttpServletRequest httpServletRequest) {


        return new DailyResponse();
    }
}
