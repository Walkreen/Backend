package capstone.walkreen.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import capstone.walkreen.dto.*;
import capstone.walkreen.entity.Daily;
import capstone.walkreen.entity.Mission;
import capstone.walkreen.entity.User;
import capstone.walkreen.entity.UserMission;
import capstone.walkreen.enumerations.MissionStatus;
import capstone.walkreen.repository.DailyRepository;
import capstone.walkreen.repository.MissionRepository;
import capstone.walkreen.repository.UserMissionRepository;
import capstone.walkreen.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final AuthService authService;

    private final UserMissionRepository userMissionRepository;
    private final DailyRepository dailyRepository;

    public MonthResponse getMonthCalendar(Integer year, Integer month, HttpServletRequest httpServletRequest) {

        final User user = authService.getUserByToken(httpServletRequest);
        final LocalDate start = LocalDate.of(year, month, 1);
        final LocalDate end = start.plusDays(start.lengthOfMonth()-1);

        MonthResponse monthResponse = MonthResponse.builder()
                .userId(user.getId())
                .calendar(year+"-"+month)
                .missionScoreResponses(new ArrayList<>())
                .build();

        Map<LocalDate, Integer> monthScore = new HashMap<LocalDate, Integer>();

        for (Daily day : dailyRepository.findDailiesByUserAndCompletionDateBetween(user, start, end)) {
            int dailyPoint = 0;

            if (day.getMissionA()){
                dailyPoint++;
            }
            if (day.getMissionB()){
                dailyPoint++;
            }
            if (day.getMissionC()){
                dailyPoint++;
            }
            if (day.getMissionD()){
                dailyPoint++;
            }
            if (day.getMissionE()){
                dailyPoint++;
            }
            monthScore.put(day.getCompletionDate(), dailyPoint);
        }

        List<UserMission> userMissions = userMissionRepository.findAllByUserAndStatus(user, MissionStatus.DONE);

        for (UserMission userMission : userMissions) {
            LocalDate localDate = userMission.getCompletionDate();

            if (monthScore.containsKey(localDate))
                monthScore.put(localDate, (monthScore.get(localDate) + 1));
            else
                monthScore.put(localDate, 1);
        }

        monthScore.forEach((date, point)->{
            System.out.println( date +" : "+ point );
            String score = null;

            if (0 < point && point <= 2)
                monthResponse.getMissionScoreResponses().add(new MissionScoreResponse(date, "C"));
            else if (2 < point && point <= 5)
                monthResponse.getMissionScoreResponses().add(new MissionScoreResponse(date, "B"));
            else
                monthResponse.getMissionScoreResponses().add(new MissionScoreResponse(date, "A"));
        });

        return monthResponse;
    }

    public DayResponse getDayCalendar(Integer year, Integer month, Integer day, HttpServletRequest httpServletRequest) {

        final User user = authService.getUserByToken(httpServletRequest);
        LocalDate localDate = LocalDate.of(year, month, day);
        DayResponse dayResponse= DayResponse.builder()
                        .userId(user.getId()).build();
        dayResponse.setDailyResponse(new DailyResponse(localDate, false, false, false, false, false));

        for (Daily daily : user.getDailyMission()){
            if (daily.getCompletionDate().equals(localDate)){
                dayResponse.setDailyResponse(DailyMapper.INSTANCE.dailyToResponse(daily));
                break;
            }
        }

        final List<UserMission> userMissions = userMissionRepository.findAllByUserAndStatusAndCompletionDate(user, MissionStatus.DONE, localDate);
        if (userMissions == null) { return dayResponse; }

        List<MissionResponse> missionResponses = new ArrayList<>();
        for (UserMission userMission : userMissions) {
            Mission mission = userMission.getMission();

            missionResponses.add(new MissionResponse(
                    mission.getId(),
                    MissionStatus.DONE,
                    mission.getTitle(),
                    mission.getContents(),
                    mission.getReward(),
                    mission.getPeople(),
                    mission.getStartTime(),
                    mission.getEndTime()));
        }
        dayResponse.setMissionResponses(missionResponses);
        return dayResponse;
    }
}
