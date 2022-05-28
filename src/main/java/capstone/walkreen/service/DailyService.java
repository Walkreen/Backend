package capstone.walkreen.service;

import capstone.walkreen.dto.DailyRequest;
import capstone.walkreen.dto.StringResponse;
import capstone.walkreen.entity.Daily;
import capstone.walkreen.entity.Mission;
import capstone.walkreen.entity.User;
import capstone.walkreen.repository.DailyRepository;
import capstone.walkreen.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class DailyService {

    private final AuthService authService;
    private final DailyRepository dailyRepository;

    public StringResponse getNowDaily(HttpServletRequest httpServletRequest) {

        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0,0,0));
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));

        final User user = authService.getUserByToken(httpServletRequest);

        System.out.println(dailyRepository.findAllByUser(user).toString());
        System.out.println(user.getDailyMission().toString());
        System.out.println(dailyRepository.findAllByUserAndCompletionDateBetween(user, startDatetime, endDatetime).toString());

        return new StringResponse("일단 오케이");
    }

    public StringResponse setDaily(HttpServletRequest httpServletRequest) {


        LocalDateTime testCaseA = LocalDateTime.of(LocalDate.now().minusDays(5), LocalTime.of(10,30,10));
        LocalDateTime testCaseB = LocalDateTime.of(LocalDate.now().minusDays(4), LocalTime.of(11,40,10));
        LocalDateTime testCaseC = LocalDateTime.of(LocalDate.now().minusDays(3), LocalTime.of(12,50,10));
        LocalDateTime testCaseD = LocalDateTime.of(LocalDate.now().minusDays(2), LocalTime.of(13,10,10));
        LocalDateTime testCaseE = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(14,20,10));

        final User user = authService.getUserByToken(httpServletRequest);

        ArrayList<Daily> testMission = new ArrayList<>();

        testMission.add(Daily.builder()
                .user(user)
                .completionDate(testCaseA)
                .missionA(false)
                .missionB(false)
                .missionC(false)
                .missionD(true)
                .missionE(true).build());

        testMission.add(Daily.builder()
                .user(user)
                .completionDate(testCaseB)
                .missionA(false)
                .missionB(false)
                .missionC(false)
                .missionD(true)
                .missionE(true).build());

        testMission.add(Daily.builder()
                .user(user)
                .completionDate(testCaseC)
                .missionA(false)
                .missionB(false)
                .missionC(false)
                .missionD(true)
                .missionE(true).build());

        testMission.add(Daily.builder()
                .user(user)
                .completionDate(testCaseD)
                .missionA(false)
                .missionB(false)
                .missionC(false)
                .missionD(true)
                .missionE(true).build());

        testMission.add(Daily.builder()
                .user(user)
                .completionDate(testCaseE)
                .missionA(false)
                .missionB(false)
                .missionC(false)
                .missionD(true)
                .missionE(true).build());

        dailyRepository.saveAll(testMission);

        return new StringResponse("가상 데일리 미션을 추가하였습니다");
    }
}
