package capstone.walkreen.service;

import capstone.walkreen.JwtUtil;
import capstone.walkreen.auth.TokenInfo;
import capstone.walkreen.auth.TokenResponse;
import capstone.walkreen.dto.*;
import capstone.walkreen.entity.User;
import capstone.walkreen.enumerations.Authority;
import capstone.walkreen.exception.InvalidPasswordException;
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

        // 중복 검사에 관한 의문.. 닉네임-이메일-
        signUpRequest.setPassword(authService.encodePassword(signUpRequest.getPassword()));

        final User user = UserMapper.INSTANCE.requestToUser(signUpRequest);

        final User savedUser = save(user);

        //savedUser.setToken(jwtUtil.generateToken(savedUser.getId(), savedUser.getEmail(), savedUser.getAuthority()));

        UserResponse userResponse = UserMapper.INSTANCE.userToResponse(savedUser);
        userResponse.setTokenResponse(jwtUtil.generateToken(getTokenInfo(savedUser)));

        return userResponse;
    }

    public UserResponse logIn(LogInRequest logInRequest) {
        // 아이디 조회
        // 첫번째 과정을 통과하면 무조건 user 가 존재할 수 밖에 없어서 exception 처리 x
        User user = userRepository.findUserByEmail(logInRequest.getEmail());

        // 비밀번호 조회
        if(!authService.isPasswordMatches(logInRequest.getPassword(),user)){
            throw new InvalidPasswordException();
        }

        UserResponse userResponse = UserMapper.INSTANCE.userToResponse(user);

        // 올바르게 있다면 토큰 생성
        userResponse.setTokenResponse(jwtUtil.generateToken(getTokenInfo(user)));

        // 해당 유저 정보와 생성된 토큰을 User response 에 저장하여 return
        return userResponse;
    }

    public User save(User user) { return userRepository.save(user);}

    private TokenInfo getTokenInfo(User user) {
        return new TokenInfo(user.getId(), user.getEmail(), user.getAuthority());
    }

    public BooleanResponse existsByEmail(String email) {

        if(userRepository.existsByEmail(email)) {
            return new BooleanResponse(true);
        }
        else { return new BooleanResponse(false); }
    }

    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }
}
