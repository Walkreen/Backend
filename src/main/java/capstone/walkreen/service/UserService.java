package capstone.walkreen.service;

import capstone.walkreen.exception.DuplicateEmailException;
import capstone.walkreen.exception.DuplicateNicknameException;
import capstone.walkreen.exception.InvalidUserException;
import capstone.walkreen.util.JwtUtil;
import capstone.walkreen.auth.TokenInfo;
import capstone.walkreen.dto.*;
import capstone.walkreen.entity.User;
import capstone.walkreen.exception.InvalidPasswordException;
import capstone.walkreen.repository.UserRepository;
import capstone.walkreen.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;


@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    public StringResponse checkEmailAvailability(EmailRequest emailRequest) {
        if (userRepository.existsByEmail(emailRequest.getEmail())) throw new DuplicateEmailException();

        return new StringResponse("사용가능한 이메일입니다");
    }

    @Transactional
    public UserResponse signUp(SignUpRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) throw new DuplicateEmailException(); // 혹시 모름
        if (userRepository.existsByNickname(signUpRequest.getNickname())) throw new DuplicateNicknameException();

        signUpRequest.setPassword(authService.encodePassword(signUpRequest.getPassword()));

        final User user = UserMapper.INSTANCE.requestToUser(signUpRequest);
        final User savedUser = userRepository.save(user);

        UserResponse userResponse = UserMapper.INSTANCE.userToResponse(savedUser);
        userResponse.setTokenResponse(jwtUtil.generateToken(getTokenInfo(savedUser)));

        return userResponse;
    }

    public UserResponse logIn(LogInRequest logInRequest) {

        final User user = userRepository.findUserByEmail(logInRequest.getEmail()).orElseThrow(InvalidUserException::new);

        if (!passwordEncoder.matches(logInRequest.getPassword(), user.getPassword())) throw new InvalidPasswordException();

        UserResponse userResponse = UserMapper.INSTANCE.userToResponse(user);
        userResponse.setTokenResponse(jwtUtil.generateToken(getTokenInfo(user)));

        return userResponse;
    }

    @Transactional
    public StringResponse resetPassword(EmailRequest emailRequest) {

        User user = userRepository.findUserByEmail(emailRequest.getEmail()).orElseThrow(InvalidUserException::new);

        final String randomPassword = PasswordUtil.randomPw();
        mailService.sendPasswordMail(emailRequest.getEmail(), randomPassword);
        user.setPassword(authService.encodePassword(randomPassword));
        userRepository.save(user);

        return new StringResponse("해당 계정의 이메일로 임시 비밀번호를 발송하였습니다");
    }

    private TokenInfo getTokenInfo(User user) { // 이거 JwtUtil로 돌릴지
        return new TokenInfo(user.getId(), user.getEmail(), user.getAuthority());
    }

}
