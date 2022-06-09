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
                .title("게임 개발하기")
                .contents("게임을 개발하고 보상을 획득하세요!")
                .reward(100)
                .people(0L)
                .startTime(LocalDate.of(2022, 3, 1))
                .endTime(LocalDate.of(2022, 6, 30)).build());

        testMission.add(Mission.builder()
                .title("운영체제 개발하기")
                .contents("운영체제를 개발하고 보상을 획득하세요!")
                .reward(150)
                .people(0L)
                .startTime(LocalDate.of(2022, 4, 1))
                .endTime(LocalDate.of(2022, 6, 30)).build());

        testMission.add(Mission.builder()
                .title("족구 개발하기")
                .contents("족구를 하고 캐리하여 획득하세요!")
                .reward(50)
                .people(0L)
                .startTime(LocalDate.of(2022, 4, 10))
                .endTime(LocalDate.of(2022, 6, 30)).build());

        testMission.add(Mission.builder()
                .title("개발 새발하기")
                .contents("개와 발의 새를 고 보상을 획득하세요!")
                .reward(100)
                .people(0L)
                .startTime(LocalDate.of(2022, 6, 15))
                .endTime(LocalDate.of(2022, 7, 20)).build());

        testMission.add(Mission.builder()
                .title("스타벅스 그린 커피 이용하기")
                .contents("환경을 지키는 제로 웨이스트 커피를 마시고 보상을 획득하세요!")
                .reward(100)
                .people(0L)
                .startTime(LocalDate.of(2022, 6, 1))
                .endTime(LocalDate.of(2022, 6, 30)).build());

        missionRepository.saveAll(testMission);

        return true;
    }
    /////////////////////////////// for manager ///////////////////////////////
}