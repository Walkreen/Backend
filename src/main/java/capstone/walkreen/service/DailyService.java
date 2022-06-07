package capstone.walkreen.service;

import capstone.walkreen.dto.DailyMapper;
import capstone.walkreen.dto.DailyResponse;
import capstone.walkreen.dto.InvalidMonthDailyException;
import capstone.walkreen.dto.StringResponse;
import capstone.walkreen.entity.Daily;
import capstone.walkreen.entity.User;
import capstone.walkreen.exception.NotExistMonthDailyException;
import capstone.walkreen.exception.NotExistTodayDailyException;
import capstone.walkreen.repository.DailyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyService {

    private final AuthService authService;
    private final DailyRepository dailyRepository;

    public DailyResponse getTodayDaily(HttpServletRequest httpServletRequest) {

        final User user = authService.getUserByToken(httpServletRequest);

        return DailyMapper.INSTANCE.dailyToResponse(findTodayDailyByUser(user));
    }

    public DailyResponse getMonthDaily(Integer year, Integer month, HttpServletRequest httpServletRequest) {

        // 값들어오는거 유효성 검사
        if (1 > month || month > 12) { throw new InvalidMonthDailyException(); };

        final LocalDate initial = LocalDate.of(year, month, 1);
        final LocalDate start = initial.withDayOfMonth(1);
        final LocalDate end = initial.withDayOfMonth(initial.getMonth().length(initial.isLeapYear()));

        final User user = authService.getUserByToken(httpServletRequest);

        final List<Daily> daily = dailyRepository.findDailyByUserAndCompletionDateBetween(user, start, end);
        if (daily.isEmpty()) { throw new NotExistMonthDailyException(); }

        for (Daily i : daily) {
            System.out.println(i.getCompletionDate());
        }

        return new DailyResponse();
    }

    public StringResponse setDaily(HttpServletRequest httpServletRequest) {


        LocalDate testCaseA = LocalDate.of(2022, 4, 1);
        LocalDate testCaseB = LocalDate.of(2021, 3, 5);
        LocalDate testCaseC = LocalDate.of(2022, 5, 10);
        LocalDate testCaseD = LocalDate.of(2022, 5, 12);
        LocalDate testCaseE = LocalDate.now();

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

    private Daily findTodayDailyByUser(User user) {

        final LocalDate today = LocalDate.now();

        for (Daily day : user.getDailyMission()) {
            System.out.println(day.getCompletionDate().toString());
            if (day.getCompletionDate().equals(today)) { return day; }
        }
        throw new NotExistTodayDailyException();
    }
}
