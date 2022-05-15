package capstone.walkreen.service;

import capstone.walkreen.dto.CancelRequest;
import capstone.walkreen.dto.EmailRequest;
import capstone.walkreen.dto.StringResponse;
import capstone.walkreen.dto.SubmitRequest;
import capstone.walkreen.entity.Mission;
import capstone.walkreen.entity.User;
import capstone.walkreen.exception.InvalidTokenException;
import capstone.walkreen.exception.InvalidUserException;
import capstone.walkreen.repository.MissionRepository;
import capstone.walkreen.repository.UserRepository;
import capstone.walkreen.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class MissionService {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final MissionRepository missionRepository;
    private final JwtUtil jwtUtil;

    public StringResponse getNormalMission(Pageable pageable, HttpServletRequest httpServletRequest) {
        return new StringResponse("일단 오케이");
    }

    public StringResponse getDailyMission(HttpServletRequest httpServletRequest) {
        final User user = getUserByToken(httpServletRequest);

        return new StringResponse("미션 제출 완료");
    }
    
    public StringResponse submitMission(SubmitRequest submitRequest, HttpServletRequest httpServletRequest) {
        return new StringResponse("미션 제출 완료");
    }
    
    public StringResponse cancelMission(CancelRequest cancelRequest, HttpServletRequest httpServletRequest) {
        return new StringResponse("미션 취소 완료");
    }

    public StringResponse testAPI(HttpServletRequest httpServletRequest) {

        return new StringResponse("일단 오케이");
    }

    private User getUserByToken(HttpServletRequest httpServletRequest) {

        final String token = authService.getTokenByHeader(httpServletRequest);

        if(jwtUtil.isTokenExpired(token)) throw new InvalidTokenException();

        return userRepository.findUserByEmail(jwtUtil.getEmailByToken(token)).orElseThrow(InvalidUserException::new);
    }

    public StringResponse setMission(EmailRequest emailRequest) {
        ArrayList<Mission> testMission = new ArrayList<>();

        testMission.add(Mission.builder()
                .title("게임 개발하기")
                .contents("게임을 개발하고 보상을 획득하세요!")
                .reward(100L)
                .startTime("05월 15일")
                .endTime("06월 15일").build());

        testMission.add(Mission.builder()
                .title("운영체제 개발하기")
                .contents("운영체제를 개발하고 보상을 획득하세요!")
                .reward(150L)
                .startTime("05월 15일")
                .endTime("06월 15일").build());

        testMission.add(Mission.builder()
                .title("족구 개발하기")
                .contents("족구를 하고 캐리하여 획득하세요!")
                .reward(50L)
                .startTime("05월 15일")
                .endTime("06월 15일").build());

        testMission.add(Mission.builder()
                .title("개발 새발하기")
                .contents("개와 발의 새를 고 보상을 획득하세요!")
                .reward(100L)
                .startTime("05월 15일")
                .endTime("06월 15일").build());

        testMission.add(Mission.builder()
                .title("스타벅스 그린 커피 이용하기")
                .contents("환경을 지키는 제로 웨이스트 커피를 마시고 보상을 획득하세요!")
                .reward(100L)
                .startTime("05월 15일")
                .endTime("06월 15일").build());

        missionRepository.saveAll(testMission);

        return new StringResponse("가상 미션을 추가하였습니다");
    }
}
