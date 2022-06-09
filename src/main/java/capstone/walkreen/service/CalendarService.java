package capstone.walkreen.service;

import java.util.List;

import capstone.walkreen.dto.*;
import capstone.walkreen.entity.Daily;
import capstone.walkreen.entity.User;
import capstone.walkreen.repository.DailyRepository;
import capstone.walkreen.repository.MissionRepository;
import capstone.walkreen.repository.UserMissionRepository;
import capstone.walkreen.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private UserMissionRepository userMissionRepository;
    private MissionRepository missionRepository;

    private final AuthService authService;
    private final DailyRepository dailyRepository;
    private final UserRepository userRepository;
    private final DailyService dailyService;


    public MonthResponse getMonthCalendar(Integer year, Integer month, HttpServletRequest httpServletRequest) {

        MonthResponse monthResponse = new MonthResponse();

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.plusDays(start.lengthOfMonth()-1);

        monthResponse.setDate(start);

        final User user = authService.getUserByToken(httpServletRequest);

        monthResponse.setUserId(user.getId());

        Integer dailyPoint = 0;
        //List<Daily> dailyList = dailyRepository.findDailiesByUserAndCompletionDateBetween(user, start, end);

        for(Daily day : dailyRepository.findDailiesByUserAndCompletionDateBetween(user, start, end)) {
            //count 포인트
            if (day.getMissionA() == true){
                dailyPoint++;
            }
            if (day.getMissionB() == true){
                dailyPoint++;
            }
            if (day.getMissionC() == true){
                dailyPoint++;
            }
            if (day.getMissionD() == true){
                dailyPoint++;
            }
            if (day.getMissionE() == true){
                dailyPoint++;
            }
            MissionScoreResponse missionScoreResponse = new MissionScoreResponse(day.getCompletionDate(), dailyPoint);
            monthResponse.getMissionScoreResponses().add(missionScoreResponse);

            dailyPoint = 0;
        }
        return monthResponse;
    }

    public DayResponse getDayCalendar(Integer year, Integer month, Integer day, HttpServletRequest httpServletRequest) {

        LocalDate localDate = LocalDate.of(year, month, day);

        DayResponse dayResponse= new DayResponse();
        dayResponse.setDailyResponse(new DailyResponse(localDate, false, false, false, false, false));

        final User user = authService.getUserByToken(httpServletRequest);

        for(Daily daily : user.getDailyMission()){
            if (daily.getCompletionDate().equals(localDate)){
                dayResponse.setDailyResponse(DailyMapper.INSTANCE.dailyToResponse(daily));
                break;
            }
        }

        return dayResponse;
    }
}
