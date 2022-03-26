package capstone.walkreen.dto;

import capstone.walkreen.enumerations.Gender;
import java.util.Date;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
public class SignUpRequest {

    @NotBlank(message = "이메일은 필수 입력 값입니다")
    @Email(message = "이메일 형식이 아닙니다")
    private String email;

    @Setter
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 알파벳, 숫자, 특수기호를 최소 1개 이상 포함해야 하며, 8자 ~ 20자의 길이여야 합니다.")
    private String password;

    @NotBlank(message = "이름은 필수 입력 값입니다")
    private String name;

    @NotBlank(message = "닉네임은 필수 입력 값입니다")
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Date birthday;
}
