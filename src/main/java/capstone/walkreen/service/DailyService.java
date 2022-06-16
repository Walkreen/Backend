package capstone.walkreen.service;

import capstone.walkreen.dto.*;
import capstone.walkreen.entity.Daily;
import capstone.walkreen.entity.User;
import capstone.walkreen.exception.NotExistMonthDailyException;
import capstone.walkreen.exception.NotExistTodayDailyException;
import capstone.walkreen.repository.DailyRepository;
import capstone.walkreen.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import static com.sun.tools.doclint.Entity;

@Service
@RequiredArgsConstructor
public class DailyService {

    private final AuthService authService;
    private final DailyRepository dailyRepository;
    private final UserRepository userRepository;

    public DailyResponse getTodayDaily(HttpServletRequest httpServletRequest) {

        final LocalDate today = LocalDate.now();

        final User user = authService.getUserByToken(httpServletRequest);
        Daily daily = findDailyByUser(today, user);

        return DailyMapper.INSTANCE.dailyToResponse(daily);
    }

    public DailyResponse setTodayDaily(DailyRequest dailyRequest, HttpServletRequest httpServletRequest) {

        final User user = authService.getUserByToken(httpServletRequest);

        Integer reward = 0;

        Daily daily = findDailyByUser(dailyRequest.getCompletionDate(),user);

        if(daily.getUser() == null) {
            switch (dailyRequest.getMission()) {
                case 'A':
                        reward = 5;
                    daily.setMissionA(true);
                    break;
                case 'B':

                        reward = 15;

                    daily.setMissionB(true);
                    break;
                case 'C':

                        reward = 10;

                    daily.setMissionC(true);
                    break;
                case 'D':

                        reward = 15;

                    daily.setMissionD(true);
                    break;
                case 'E':

                        reward = 10;

                    daily.setMissionE(true);
                    break;
                default:
                    break;
            }
        }

        else{
        switch (dailyRequest.getMission()) {
                case 'A':
                    if ((daily.getUser() == user) && (daily.getMissionA() == false || daily.getMissionA() == null)) {
                        reward = 5;
                    }
                    daily.setMissionA(true);
                    break;
                case 'B':
                    if ((daily.getUser() == user) && (daily.getMissionB() == false || daily.getMissionB() == null)) {
                        reward = 15;
                    }
                    daily.setMissionB(true);
                    break;
                case 'C':
                    if ((daily.getUser() == user) && (daily.getMissionC() == false || daily.getMissionC() == null)) {
                        reward = 10;
                    }
                    daily.setMissionC(true);
                    break;
                case 'D':
                    if ((daily.getUser() == user) && (daily.getMissionD() == false || daily.getMissionD() == null)) {
                        reward = 15;
                    }
                    daily.setMissionD(true);
                    break;
                case 'E':
                    if ((daily.getUser() == user) && (daily.getMissionE() == false || daily.getMissionE() == null)) {
                        reward = 10;
                    }
                    daily.setMissionE(true);
                    break;
                default:
                    break;
            }

        }

        //if (daily.getUser().equals(null)){
            daily.setUser(user);
        //}

        dailyRepository.save(daily);

        System.out.println(daily.getCompletionDate().toString());


        //user.getDailyMission().add(daily);
        System.out.println(user.getDailyMission().toString());
        reward += user.getPrepoint();
        int acc = reward + user.getAccpoint();
        user.setAccpoint(acc);
        user.setPrepoint(reward);

        //dailyRepository.save(testMission);

        userRepository.save(user);

        DailyResponse dailyResponse = DailyMapper.INSTANCE.dailyToResponse(daily);
        return dailyResponse;
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

    private Daily findDailyByUser(LocalDate date, User user) {
        //final LocalDate today = LocalDate.now();
        //System.out.println("for문 시작");
        for (Daily day : user.getDailyMission()) {
            //System.out.println("1");
            //System.out.println(day.getCompletionDate().toString());
            if (day.getCompletionDate().equals(date)) {

                System.out.println("존재");
                return day;
            }
        }
        //throw new NotExistTodayDailyException();
        System.out.println("For 문 끝");
        //전부 false 인 새로운 entity return
        return Daily.builder().user(null)
                .completionDate(date)
                .missionA(false)
                .missionB(false)
                .missionC(false)
                .missionD(false)
                .missionE(false).build();

        //Daily daily = dailyRepository.findDailyByUserAndCompletionDate(user, date).orElseThrow(Exception)

        }

        public DinfoResponse getDailyInfo(HttpServletRequest httpServletRequest){

            Integer count = 0;
            Integer MAsum = 0;
            Integer MBsum = 0;
            Integer MCsum = 0;
            Integer MDsum = 0;
            Integer MEsum = 0;

            final User user = authService.getUserByToken(httpServletRequest);

            for (Daily day : user.getDailyMission()) {
                count++;

                if(day.getMissionA()) {
                    MAsum++;
                }
                if(day.getMissionB()){
                    MBsum++;
                }
                if(day.getMissionC()){
                    MCsum++;
                }
                if(day.getMissionD()){
                    MDsum++;
                }
                if(day.getMissionE()){
                    MEsum++;
                }
            }
            DinfoResponse dinfoResponse = new DinfoResponse(count,MAsum,MBsum,MCsum,MDsum,MEsum);
            return dinfoResponse;
    }
}
