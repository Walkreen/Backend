package capstone.walkreen.service;

import capstone.walkreen.dto.*;
import capstone.walkreen.entity.Mission;
import capstone.walkreen.entity.User;
import capstone.walkreen.entity.UserMission;
import capstone.walkreen.enumerations.MissionStatus;
import capstone.walkreen.exception.NonExistMissionException;
import capstone.walkreen.repository.MissionRepository;
import capstone.walkreen.repository.UserMissionRepository;
import capstone.walkreen.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;
    private final UserMissionRepository userMissionRepository;
    private final UserRepository userRepository;

    public PageMissionResponse getCan(User user) {
        final LocalDate today = LocalDate.now();
        List<Mission> missions;

        List<UserMission> userMissions = userMissionRepository.findAllByUser(user);
        if (userMissions.isEmpty()) {
            missions = missionRepository.findAll();
        }
        else {
            List<Long> missionIds = userMissionRepository.findAllByUser(user)
                    .stream().map(userMission -> userMission.getMission().getId()).collect(Collectors.toList());
            missions = missionRepository.findAllByIdIsNotIn(missionIds);
        }

        List<MissionResponse> missionResponses = new ArrayList<>();
        for (Mission mission : missions) {
            missionResponses.add(new MissionResponse(
                    mission.getId(),
                    MissionStatus.CAN,
                    mission.getTitle(),
                    mission.getContents(),
                    mission.getReward(),
                    mission.getPeople(),
                    mission.getStartTime(),
                    mission.getEndTime()));
        }

        return new PageMissionResponse(user.getId(), missionResponses);
    }

    public PageMissionResponse getGoing(User user) {
        final LocalDate today = LocalDate.now();

        List<UserMission> goingMissions = userMissionRepository.findAllByUserAndStatus(user, MissionStatus.GOING);
        PageMissionResponse pageMissionResponse = new PageMissionResponse();
        pageMissionResponse.setUserId(user.getId());

        List<MissionResponse> missionResponses = new ArrayList<>();

        for (UserMission userMission : goingMissions) {
            System.out.println(userMission.getId().toString());
            Mission mission = userMission.getMission();

            if (mission.getEndTime().isAfter(today)) {
                missionResponses.add(MissionResponse.builder()
                        .missionId(mission.getId())
                        .status(MissionStatus.GOING)
                        .title(mission.getTitle())
                        .contents(mission.getContents())
                        .reward(mission.getReward())
                        .people(mission.getPeople())
                        .startTime(mission.getStartTime())
                        .endTime(mission.getEndTime()).build());
            }
        }
        pageMissionResponse.setMissions(missionResponses);

        return pageMissionResponse;
    }

    public PageMissionResponse getDone(User user) {
        List<UserMission> doneMissions = userMissionRepository.findAllByUserAndStatus(user, MissionStatus.DONE);
        PageMissionResponse pageMissionResponse = new PageMissionResponse();
        pageMissionResponse.setUserId(user.getId());

        List<MissionResponse> missionResponses = new ArrayList<>();

        for (UserMission userMission : doneMissions) {
            Mission mission = userMission.getMission();

            missionResponses.add(MissionResponse.builder()
                    .missionId(mission.getId())
                    .status(MissionStatus.DONE)
                    .title(mission.getTitle())
                    .contents(mission.getContents())
                    .reward(mission.getReward())
                    .people(mission.getPeople())
                    .startTime(mission.getStartTime())
                    .endTime(mission.getEndTime()).build());
        }
        pageMissionResponse.setMissions(missionResponses);

        return pageMissionResponse;
    }

    @Transactional
    public MissionResponse join(Long missionId, User user) {
        System.out.println("오류1");
        Mission mission = missionRepository.getById(missionId);
        System.out.println("오류2");
        final UserMission savedUserMission = userMissionRepository.save(new UserMission(user, mission, MissionStatus.GOING));

        mission.setPeople(mission.getPeople() + 1);
        final Mission savedMission = missionRepository.save(mission);

        return MissionResponse.builder()
                .missionId(savedMission.getId())
                .status(savedUserMission.getStatus())
                .title(savedMission.getTitle())
                .contents(savedMission.getContents())
                .reward(savedMission.getReward())
                .people(savedMission.getPeople())
                .startTime(savedMission.getStartTime())
                .endTime(savedMission.getEndTime()).build();
    }

    @Transactional
    public MissionResponse cancel(Long missionId, User user) {
        Mission mission = missionRepository.getById(missionId);

        userMissionRepository.delete(findByMission(user, mission));

        mission.setPeople(mission.getPeople() - 1);
        final Mission savedMission = missionRepository.save(mission);

        return MissionResponse.builder()
                .missionId(savedMission.getId())
                .status(MissionStatus.NONE)
                .title(savedMission.getTitle())
                .contents(savedMission.getContents())
                .reward(savedMission.getReward())
                .people(savedMission.getPeople())
                .startTime(savedMission.getStartTime())
                .endTime(savedMission.getEndTime()).build();
    }

    @Transactional
    public MissionResponse submit(Long missionId, User user) {
        final Mission mission = missionRepository.getById(missionId);

        UserMission userMission = findByMission(user, mission);
        userMission.setStatus(MissionStatus.DONE);
        userMission.setCompletionDate(LocalDate.now());
        userMissionRepository.save(userMission);

        user.setPrepoint(user.getPrepoint() + mission.getReward());
        user.setAccpoint(user.getAccpoint() + mission.getReward());
        userRepository.save(user);

        return MissionResponse.builder()
                .missionId(mission.getId())
                .status(userMission.getStatus())
                .title(mission.getTitle())
                .contents(mission.getContents())
                .reward(mission.getReward())
                .people(mission.getPeople())
                .startTime(mission.getStartTime())
                .endTime(mission.getEndTime()).build();
    }

    private UserMission findByMission(User user, Mission mission) {

        for (UserMission userMission : user.getMissions()) {
            if (userMission.getMission().equals(mission)) { return userMission; }
        }
        throw new NonExistMissionException();
    }

    /////////////////////////////// for manager ///////////////////////////////
    public PageMissionResponse zget() {
        List<MissionResponse> msr = new ArrayList<>();

        List<Mission> missions = missionRepository.findAll();

        for (Mission mission : missions) {
            msr.add(MissionResponse.builder()
                    .missionId(mission.getId())
                    .status(MissionStatus.NONE)
                    .title(mission.getTitle())
                    .contents(mission.getContents())
                    .reward(mission.getReward())
                    .people(mission.getPeople())
                    .startTime(mission.getStartTime())
                    .endTime(mission.getEndTime()).build());
        }
        return PageMissionResponse.builder().missions(msr).build();

    }


    public MissionResponse zset(SetMissionRequest setMissionRequest) {
        final Mission mission = Mission.builder()
                .title(setMissionRequest.getTitle())
                .contents(setMissionRequest.getContent())
                .reward(setMissionRequest.getReward())
                .people(0L)
                .startTime(setMissionRequest.getStartTime())
                .endTime(setMissionRequest.getEndTime()).build();

        final Mission savedMission = missionRepository.save(mission);

        return MissionResponse.builder()
                .missionId(savedMission.getId())
                .status(MissionStatus.NONE)
                .title(savedMission.getTitle())
                .contents(savedMission.getContents())
                .reward(savedMission.getReward())
                .people(savedMission.getPeople())
                .startTime(savedMission.getStartTime())
                .endTime(savedMission.getEndTime()).build();
    }

    public Boolean zdelete(Long missionId) {
        missionRepository.deleteById(missionId); return true;
    }

    public Boolean zmake() {
        ArrayList<Mission> testMission = new ArrayList<>();

        testMission.add(Mission.builder()
                .title("오늘도 분리수거 왕!")
                .contents("분리수거는 폐기물의 소각 및 재활용 등 처분을 용이하게 하기 위해, " +
                        "그 재질마다 폐기물을 분류하고 그것을 수집하는 것을 말합니다. " +
                        "우리가 소비한 자원을 재사용이 용이하게 우리 분리수거를 하는 것은 어떤가요?")
                .reward(15)
                .people(0L)
                .startTime(LocalDate.of(2022, 3, 1))
                .endTime(LocalDate.of(2022, 6, 30)).build());

        testMission.add(Mission.builder()
                .title("컴퓨터 사용 안 할 때 코드 뽑기!")
                .contents("컴퓨터는 사용하지 않을 때도 전원이 들어갈 수 있습니다. " +
                        "이는 곧 의도하지 않은 전력을 소비하거나, 안전 사고를 야기할 수 있습니다. " +
                        "사용하지 않는 컴퓨터에 코드를 뽑아 환경을 보호하는 것은 어떤가요?")
                .reward(15)
                .people(0L)
                .startTime(LocalDate.of(2022, 4, 1))
                .endTime(LocalDate.of(2022, 6, 30)).build());

        testMission.add(Mission.builder()
                .title("한라산 플로깅 투어")
                .contents("한라산은 대한민국에서 가장 큰 제주도에 있는 가장 높은 산입니다. " +
                        "또한, 인기있는 관광지로, 매년 많은 사람들이 방문합니다. " +
                        "여러분도 산에 있는 쓰레기를 줍고 하이킹하는 플로깅을 한라산에서 해보는 것은 어떤가요?")
                .reward(40)
                .people(0L)
                .startTime(LocalDate.of(2022, 4, 10))
                .endTime(LocalDate.of(2022, 6, 30)).build());

        testMission.add(Mission.builder()
                .title("북한산 플로깅 + 하이킹")
                .contents("북한산에서 플로깅을 진행합니다! 플로깅과 하이킹을 결합한 신개념 환경 보호 활동! " +
                        "우리 플로깅 크루와 함께 흥겨운 하이킹, " +
                        "보람찬 환경보호를 실천해요!\n" +
                        "전화번호) 031-8005-xxxx, xxxx")
                .reward(30)
                .people(0L)
                .startTime(LocalDate.of(2022, 6, 15))
                .endTime(LocalDate.of(2022, 7, 20)).build());

        testMission.add(Mission.builder()
                .title("안 읽은 메일 모두 삭제하기")
                .contents("읽지 않은 메일은 인터넷 네트워크의 자원 낭비를 초래합니다. " +
                        "어딘가 데이터로 남아있는 여러분의 정보를 처리하고 전송해야하기 때문인데요, " +
                        "사용하는 이메일 계정에 읽지 않은 메일을 삭제하여 환경에 도움을 주는 것은 어떤가요?")
                .reward(10)
                .people(0L)
                .startTime(LocalDate.of(2022, 6, 1))
                .endTime(LocalDate.of(2022, 6, 30)).build());

        testMission.add(Mission.builder()
                .title("제로 웨이스트 카페 방문하기")
                .contents("환경을 지키는 모범적인 카페 '제로 웨이스트 카페'를 이용하고 " +
                        "보상을 획득하세요!")
                .reward(10)
                .people(0L)
                .startTime(LocalDate.of(2022, 6, 1))
                .endTime(LocalDate.of(2022, 6, 30)).build());

        missionRepository.saveAll(testMission);

        return true;
    }
    /////////////////////////////// for manager ///////////////////////////////
}