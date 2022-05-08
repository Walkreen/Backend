package capstone.walkreen.entity;

import capstone.walkreen.auth.TokenResponse;
import capstone.walkreen.dto.SignUpRequest;
import capstone.walkreen.enumerations.Authority;
import capstone.walkreen.enumerations.Gender;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class User extends BaseEntity {

    private String email;

    @Setter
    private String password;

    private String name;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Date birthday;

    @Enumerated(EnumType.STRING)
    private Authority authority = Authority.USER;

//
//    @Column(columnDefinition = "varchar(400)")
//    private String accessToken;
//
//    @Column(columnDefinition = "varchar(400)")
//    private String refreshToken;
//
//
//    public void setToken(TokenResponse tokenResponse) {
//        this.accessToken = tokenResponse.getAccessToken();
//        this.refreshToken = tokenResponse.getRefreshToken();
//    }
}
