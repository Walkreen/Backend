package capstone.walkreen.auth;

import capstone.walkreen.enumerations.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenInfo {
    private Long id;
    private String email;
    private Authority authority;
}
