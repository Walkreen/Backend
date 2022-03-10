package capstone.walkreen.service;

import capstone.walkreen.JwtUtil;
import capstone.walkreen.auth.TokenInfo;
import capstone.walkreen.auth.TokenResponse;
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

    public User save(User user) { return userRepository.save(user);}

    private TokenInfo getTokenInfo(User user) {
        return new TokenInfo(user.getId(), user.getEmail(), user.getAuthority());
    }
}
