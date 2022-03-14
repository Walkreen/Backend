package capstone.walkreen.service;

import capstone.walkreen.JwtUtil;
import capstone.walkreen.auth.TokenInfo;
import capstone.walkreen.auth.TokenResponse;
import capstone.walkreen.dto.LogInRequest;
import capstone.walkreen.dto.SignUpRequest;
import capstone.walkreen.dto.UserMapper;
import capstone.walkreen.dto.UserResponse;
import capstone.walkreen.entity.User;
import capstone.walkreen.enumerations.Authority;
import capstone.walkreen.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AuthService authService;

    @Transactional
    public UserResponse signUp(SignUpRequest signUpRequest) {

        signUpRequest.setPassword(authService.encodePassword(signUpRequest.getPassword()));

        final User user = UserMapper.INSTANCE.requestToUser(signUpRequest);

        final User savedUser = save(user);

        //savedUser.setToken(jwtUtil.generateToken(savedUser.getId(), savedUser.getEmail(), savedUser.getAuthority()));

        UserResponse userResponse = UserMapper.INSTANCE.userToResponse(savedUser);
        userResponse.setTokenResponse(jwtUtil.generateToken(getTokenInfo(savedUser)));

        return userResponse;
    }

    public UserResponse logIn(LogInRequest logInRequest){
        // 아이디 조회
        // 비밀번호 조회
        // 올바르게 있다면 토큰 생성
        // 해당 유저 정보와 생성된 토큰을 User response 에 저장하여 return
    }

    public User save(User user) { return userRepository.save(user);}

    private TokenInfo getTokenInfo(User user) {
        return new TokenInfo(user.getId(), user.getEmail(), user.getAuthority());
    }



}
