package capstone.walkreen.dto;

import capstone.walkreen.auth.TokenResponse;
import capstone.walkreen.enumerations.Authority;
import capstone.walkreen.enumerations.Gender;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String email;

    private String name;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Date birthday;

    @Setter
    private TokenResponse tokenResponse;
}
