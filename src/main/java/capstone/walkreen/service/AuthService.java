package capstone.walkreen.service;

import capstone.walkreen.exception.IllegalTokenException;
import com.google.common.net.HttpHeaders;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public String getTokenByHeader(HttpServletRequest httpServletRequest) {
        final String authHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) throw new IllegalTokenException();

        return authHeader.substring("Bearer ".length());
    }
}
