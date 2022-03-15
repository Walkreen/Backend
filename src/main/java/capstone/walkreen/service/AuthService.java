package capstone.walkreen.service;

import capstone.walkreen.entity.User;
import capstone.walkreen.exception.InvalidPasswordException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    public String encodePassword(String password) {
        String encoded = passwordEncoder.encode(password);

        return encoded;
    }

    public boolean isPasswordMatches(String password, User user) {
        if (passwordEncoder.matches(password, user.getPassword())) {
            return true;
        } else {
            return false;
        }
    }
}